package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.LogoutDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.FailureDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.LoginDtoResponse;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.json.converter.JSONConverter;
import io.deeplay.intership.service.UserService;
import org.apache.log4j.Logger;


public class UserController {
    private final Logger logger = Logger.getLogger(UserController.class);
    private final UserService userService;
    private final JSONConverter jsonConverter;
    private final int clientId;

    public UserController(UserService userService, JSONConverter jsonConverter, int clientId) {
        this.userService = userService;
        this.jsonConverter = jsonConverter;
        this.clientId = clientId;
    }

    public UserController(int clientId) {
        this(new UserService(), new JSONConverter(), clientId);
    }

    public String registerUser(RegistrationDtoRequest dtoRequest) {
        final String message = String.format("Client %d send registration", clientId);
        logger.debug(message);

        try {
            final InfoDtoResponse response = userService.register(dtoRequest);
            return jsonConverter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients registration was failed");
            return getFailureResponse(ex);
        }
    }

    public String login(LoginDtoRequest dtoRequest) {
        final String message = String.format("Client %d send authorization", clientId);
        logger.debug(message);

        try {
            final LoginDtoResponse response = userService.authorization(dtoRequest);
            return jsonConverter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients login was failed");
            return getFailureResponse(ex);
        }
    }

    public String logout(LogoutDtoRequest dtoRequest) {
        final String message = String.format("Client %d send authorization", clientId);
        logger.debug(message);

        try {
            final InfoDtoResponse response = userService.logout(dtoRequest);
            return jsonConverter.getJsonFromObject(response);
        } catch (ServerException ex) {
            logger.debug("Clients logout was failed");
            return getFailureResponse(ex);
        }
    }

    private String getFailureResponse(ServerException ex) {
        final FailureDtoResponse dtoResponse = new FailureDtoResponse(ResponseStatus.FAILURE, ex.message);
        return jsonConverter.getJsonFromObject(dtoResponse);
    }
}
