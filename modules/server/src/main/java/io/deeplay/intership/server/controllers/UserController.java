package io.deeplay.intership.server.controllers;

import io.deeplay.intership.dto.request.authorization.LoginDtoRequest;
import io.deeplay.intership.dto.request.authorization.LogoutDtoRequest;
import io.deeplay.intership.dto.request.authorization.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.authorization.LoginDtoResponse;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.service.UserService;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.apache.log4j.Logger;


/**
 * Контроллер пользователей, расширяющий базовый класс Controller.
 * Отвечает за обработку запросов, связанных с регистрацией, входом и выходом пользователей.
 */
public class UserController extends Controller {
    private final Logger logger = Logger.getLogger(UserController.class);
    private final UserService userService;

    public UserController(final UserService userService, final Validator validator, final int clientId) {
        super(validator, clientId);
        this.userService = userService;
    }

    public UserController(final DataCollectionsAggregator collectionsAggregator, final int clientId) {
        this(new UserService(collectionsAggregator), new Validator(), clientId);
    }

    /**
     * Метод для регистрации нового пользователя на основе запроса {@link RegistrationDtoRequest}.
     *
     * @param dtoRequest {@link InfoDtoResponse} на регистрацию нового пользователя.
     * @return Ответ на запрос, содержащий информацию о результате регистрации.
     */
    public BaseDtoResponse registerUser(final RegistrationDtoRequest dtoRequest) {
        final String message = String.format("Client %d send registration", clientId);
        logger.debug(message);

        try {
            dtoValidator.validationRegistrationDto(dtoRequest);
            return userService.register(dtoRequest);
        } catch (ServerException ex) {
            logger.debug("Clients registration was failed");
            return getFailureResponse(ex);
        }
    }

    /**
     * Метод для входа пользователя на основе запроса {@link LoginDtoRequest}.
     *
     * @param dtoRequest {@link LoginDtoResponse} на вход пользователя.
     * @return Ответ на запрос, содержащий информацию о результате входа.
     */
    public BaseDtoResponse login(final LoginDtoRequest dtoRequest) {
        final String message = String.format("Client %d send authorization", clientId);
        logger.debug(message);

        try {
            dtoValidator.validationLoginDto(dtoRequest);
            return userService.login(dtoRequest);
        } catch (ServerException ex) {
            logger.debug("Clients name was failed");
            return getFailureResponse(ex);
        }
    }

    /**
     * Метод для выхода пользователя на основе запроса {@link LogoutDtoRequest}.
     *
     * @param dtoRequest {@link InfoDtoResponse} на выход пользователя.
     * @return Ответ на запрос, содержащий информацию о результате выхода.
     */
    public BaseDtoResponse logout(final LogoutDtoRequest dtoRequest) {
        final String message = String.format("Client %d send authorization", clientId);
        logger.debug(message);
        try {
            dtoValidator.validationLogoutDto(dtoRequest);
            return userService.logout(dtoRequest);
        } catch (ServerException ex) {
            logger.debug("Clients logout was failed");
            return getFailureResponse(ex);
        }
    }
}
