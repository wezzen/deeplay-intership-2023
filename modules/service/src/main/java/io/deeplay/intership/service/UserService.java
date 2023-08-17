package io.deeplay.intership.service;


import io.deeplay.intership.dto.ResponseInfoMessage;
import io.deeplay.intership.dto.ResponseStatus;
import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.LogoutDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.LoginDtoResponse;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.User;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserService {
    private static final String CREDENTIALS_FILE_NAME = "credentials.txt";
    private static final ConcurrentMap<String, User> authorizedUsers = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, User> loginToUser = new ConcurrentHashMap<>();
    private final Logger logger = Logger.getLogger(UserService.class);
    private final Validator dtoValidator;

    public UserService() {
        this.dtoValidator = new Validator();
    }

    public boolean isAuthorized(final String token) {
        return authorizedUsers.containsKey(token);
    }

    public LoginDtoResponse authorization(final LoginDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationLoginDto(dtoRequest);

        User user = findUserByLogin(dtoRequest.login())
                .orElseThrow(() -> new ServerException(ErrorCode.NOT_FOUND_LOGIN));
        if (!user.passwordHash().equals(dtoRequest.passwordHash())) {
            logger.debug("Incorrect password");
            throw new ServerException(ErrorCode.INVALID_AUTHORIZATION);
        }

        removeTokenIfExist(user);
        String newToken = UUID.randomUUID().toString();
        authorizedUsers.put(newToken, user);
        loginToUser.put(user.login(), user);
        return new LoginDtoResponse(
                ResponseInfoMessage.SUCCESS_AUTHORIZATION.message,
                ResponseStatus.SUCCESS.text,
                newToken);
    }

    public InfoDtoResponse register(final RegistrationDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationRegistrationDto(dtoRequest);
        if (findUserByLogin(dtoRequest.login()).isPresent()) {
            throw new ServerException(ErrorCode.LOGIN_IS_EXIST);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE_NAME, true))) {
            writer.write(dtoRequest.login() + ":" + dtoRequest.passwordHash());
            writer.newLine();
            logger.debug("Login and password have been successfully written to the file.");
        } catch (IOException ex) {
            logger.debug("Error writing username and password: " + ex.getMessage());
            throw new ServerException(ErrorCode.SERVER_EXCEPTION);
        }

        logger.debug("User was successfully registered");
        return new InfoDtoResponse(
                ResponseInfoMessage.SUCCESS_REGISTRATION.message,
                ResponseStatus.SUCCESS.text);
    }

    public InfoDtoResponse logout(final LogoutDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationLogoutDto(dtoRequest);
        User user = findUserByToken(dtoRequest.token())
                .orElseThrow(() -> new ServerException(ErrorCode.NOT_AUTHORIZED));

        loginToUser.remove(user.login());
        authorizedUsers.remove(dtoRequest.token());

        logger.debug("User was successfully logout");
        return new InfoDtoResponse(ResponseInfoMessage.SUCCESS_LOGOUT.message, ResponseStatus.SUCCESS.text);
    }

    public Optional<User> findUserByLogin(final String login) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(login)) {
                    logger.debug("User was found");
                    return Optional.of(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException ex) {
            logger.debug("Login search error: " + ex.getMessage());
        }
        return Optional.empty();
    }

    public Optional<User> findUserByToken(final String token) {
        return Optional.ofNullable(authorizedUsers.get(token));
    }

    private Optional<User> findUserByAuthorizedList(final User user) {
        return Optional.ofNullable(loginToUser.get(user.login()));
    }

    private void removeTokenIfExist(final User user) {
        Optional<User> foundUser = findUserByAuthorizedList(user);
        if (foundUser.isPresent()) {
            authorizedUsers.remove(foundUser.get().token());
            loginToUser.remove(foundUser.get().login());
        }
    }
}
