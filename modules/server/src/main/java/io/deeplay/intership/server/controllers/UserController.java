package io.deeplay.intership.server.controllers;

import io.deeplay.intership.dto.request.authorization.LoginDtoRequest;
import io.deeplay.intership.dto.request.authorization.LogoutDtoRequest;
import io.deeplay.intership.dto.request.authorization.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.User;
import io.deeplay.intership.service.UserService;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.apache.log4j.Logger;


public class UserController extends Controller {
    private final Logger logger = Logger.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService, Validator validator, int clientId) {
        super(validator, clientId);
        this.userService = userService;
    }

    public UserController(DataCollectionsAggregator collectionsAggregator, int clientId) {
        this(new UserService(collectionsAggregator), new Validator(), clientId);
    }

    public BaseDtoResponse registerUser(RegistrationDtoRequest dtoRequest) {
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

    public BaseDtoResponse login(LoginDtoRequest dtoRequest) {
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

    public BaseDtoResponse logout(LogoutDtoRequest dtoRequest) {
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