package io.deeplay.intership.service;


import io.deeplay.intership.dao.UserDao;
import io.deeplay.intership.dao.file.UserDaoImpl;
import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.LogoutDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.LoginDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.User;
import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * Класс {@code UserService} предоставляет функциональные возможности для аутентификации, регистрации и управления
 * пользователями.
 */
public class UserService {
    private final Logger logger = Logger.getLogger(UserService.class);
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserService() {
        this(new UserDaoImpl());
    }

    /**
     * Проверяет, авторизован ли пользователь с данным токеном.
     *
     * @param token Маркер аутентификации пользователя.
     * @return {@code true}, если авторизован, {@code false} в противном случае.
     */
    public boolean isAuthorized(final String token) throws ServerException {
        return userDao.getUserByToken(token) != null;
    }

    /**
     * Выполняет авторизацию пользователя на основе предоставленных учетных данных для входа.
     *
     * @param dtoRequest Объект {@link LoginDtoRequest}, содержащий информацию для входа.
     * @return {@link LoginDtoResponse}, указывающий результат авторизации.
     * @throws ServerException Если при авторизации возникает ошибка.
     */
    public LoginDtoResponse authorization(final LoginDtoRequest dtoRequest) throws ServerException {
        User currentUser = userDao.getUserByLogin(dtoRequest.login)
                .orElseThrow(() -> new ServerException(ErrorCode.NOT_FOUND_LOGIN));
        if (!currentUser.passwordHash().equals(dtoRequest.passwordHash)) {
            logger.debug("Incorrect password");
            throw new ServerException(ErrorCode.INVALID_AUTHORIZATION);
        }

        String newToken = UUID.randomUUID().toString();
        User user = new User(currentUser.login(), currentUser.passwordHash(), newToken);
        userDao.authorizeUser(user, newToken);

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
        if (userDao.getUserByLogin(dtoRequest.login).isPresent()) {
            throw new ServerException(ErrorCode.LOGIN_IS_EXIST);
        }
        userDao.addUser(new User(dtoRequest.login, dtoRequest.passwordHash));

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
        userDao.getUserByToken(dtoRequest.token);
        userDao.removeAuth(dtoRequest.token);

        logger.debug("User was successfully logout");
        return new InfoDtoResponse(ResponseStatus.SUCCESS, ResponseInfoMessage.SUCCESS_LOGOUT.message);
    }
}
