package io.deeplay.intership.service;


import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.LogoutDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.LoginDtoResponse;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ServerErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.User;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Класс {@code UserService} предоставляет функциональные возможности для аутентификации, регистрации и управления
 * пользователями.
 */
public class UserService {
    private static final String CREDENTIALS_FILE_NAME = "credentials.txt";
    private static final ConcurrentMap<String, User> AUTHORIZED_USERS = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, User> LOGIN_TO_USER = new ConcurrentHashMap<>();
    private final Logger logger;
    private final Validator dtoValidator;

    public UserService() {
        logger = Logger.getLogger(UserService.class);
        dtoValidator = new Validator();
    }

    /**
     * Проверяет, авторизован ли пользователь с данным токеном.
     *
     * @param token Маркер аутентификации пользователя.
     * @return {@code true}, если авторизован, {@code false} в противном случае.
     */
    public boolean isAuthorized(final String token) {
        return AUTHORIZED_USERS.containsKey(token);
    }

    /**
     * Выполняет авторизацию пользователя на основе предоставленных учетных данных для входа.
     *
     * @param dtoRequest Объект {@link LoginDtoRequest}, содержащий информацию для входа.
     * @return {@link LoginDtoResponse}, указывающий результат авторизации.
     * @throws ServerException Если при авторизации возникает ошибка.
     */
    public LoginDtoResponse authorization(final LoginDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationLoginDto(dtoRequest);

        User currentUser = findUserByLogin(dtoRequest.login)
                .orElseThrow(() -> new ServerException(ServerErrorCode.NOT_FOUND_LOGIN));
        if (!currentUser.passwordHash().equals(dtoRequest.passwordHash)) {
            logger.debug("Incorrect password");
            throw new ServerException(ServerErrorCode.INVALID_AUTHORIZATION);
        }

        removeTokenIfExist(currentUser);
        String newToken = UUID.randomUUID().toString();
        User user = new User(currentUser.login(), currentUser.passwordHash(), newToken);
        AUTHORIZED_USERS.put(newToken, user);
        LOGIN_TO_USER.put(currentUser.login(), user);

        return new LoginDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_AUTHORIZATION.message,
                newToken);
    }

    /**
     * Регистрирует нового пользователя с предоставленными регистрационными данными.
     *
     * @param dtoRequest {@link RegistrationDtoRequest}, содержащий регистрационную информацию.
     * @return {@link InfoDtoResponse}, указывающий результат регистрации.
     * @throws ServerException Если при регистрации возникает ошибка.
     */
    public InfoDtoResponse register(final RegistrationDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationRegistrationDto(dtoRequest);
        if (findUserByLogin(dtoRequest.login).isPresent()) {
            throw new ServerException(ServerErrorCode.LOGIN_IS_EXIST);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE_NAME, true))) {
            writer.write(dtoRequest.login + ":" + dtoRequest.passwordHash);
            writer.newLine();
            logger.debug("Login and password have been successfully written to the file.");
        } catch (IOException ex) {
            logger.debug("Error writing username and password: " + ex.getMessage());
            throw new ServerException(ServerErrorCode.SERVER_EXCEPTION);
        }

        logger.debug("User was successfully registered");
        return new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_REGISTRATION.message);
    }

    /**
     * Выполняет выход пользователя с предоставленным токеном.
     *
     * @param dtoRequest {@link LogoutDtoRequest}, содержащий токен для выхода из системы.
     * @return {@link InfoDtoResponse}, указывающий результат выхода из системы.
     * @throws ServerException Если во время выхода из системы возникает ошибка.
     */
    public InfoDtoResponse logout(final LogoutDtoRequest dtoRequest) throws ServerException {
        dtoValidator.validationLogoutDto(dtoRequest);
        User user = findUserByToken(dtoRequest.token);

        LOGIN_TO_USER.remove(user.login());
        AUTHORIZED_USERS.remove(dtoRequest.token);

        logger.debug("User was successfully logout");
        return new InfoDtoResponse(ResponseStatus.SUCCESS, ResponseInfoMessage.SUCCESS_LOGOUT.message);
    }

    /**
     * Находит пользователя по его логину в файле учетных данных.
     *
     * @param login логин пользователя.
     * @return {@link Optional} содержащий пользователя {@link User}, если он найден, или пустой, если
     * пользователь с таким логином не найден не найден.
     */
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

    /**
     * Находит пользователя по его токену аутентификации.
     *
     * @param token Маркер аутентификации пользователя.
     * @return {@link User}, если он найден, или пустой, если
     * * пользователь с таким логином не найден не найден.
     */
    public User findUserByToken(final String token) throws ServerException {
        User user = AUTHORIZED_USERS.get(token);
        if (user == null) {
            throw new ServerException(ServerErrorCode.NOT_AUTHORIZED);
        }
        return user;
    }

    private Optional<User> findUserIntoAuthorizedList(final String login) {
        return Optional.ofNullable(LOGIN_TO_USER.get(login));
    }

    private void removeTokenIfExist(final User user) {
        Optional<User> foundUser = findUserIntoAuthorizedList(user.login());
        if (foundUser.isPresent()) {
            AUTHORIZED_USERS.remove(foundUser.get().token());
            LOGIN_TO_USER.remove(foundUser.get().login());
        }
    }
}
