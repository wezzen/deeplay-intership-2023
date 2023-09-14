package io.deeplay.intership.client;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.request.authorization.LoginDtoRequest;
import io.deeplay.intership.dto.request.authorization.LogoutDtoRequest;
import io.deeplay.intership.dto.request.authorization.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.authorization.LoginDtoResponse;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.ui.UserInterface;
import org.apache.log4j.Logger;

import java.io.IOException;

public class AuthorizationController {
    private final Logger logger = Logger.getLogger(AuthorizationController.class);
    private final StreamConnector streamConnector;
    private final UserInterface userInterface;
    private final DecisionMaker decisionMaker;

    public AuthorizationController(StreamConnector streamConnector, UserInterface userInterface, DecisionMaker decisionMaker) {
        this.streamConnector = streamConnector;
        this.userInterface = userInterface;
        this.decisionMaker = decisionMaker;
    }

    public String authorizeClient() {
        BaseDtoResponse response;
        String token = null;

        while (token == null) {
            try {
                userInterface.showAuthorizationActions();
                LoginPassword loginPassword = decisionMaker.getLoginPassword();
                response = switch (loginPassword.type()) {
                    case REGISTRATION -> registration(loginPassword);
                    case LOGIN -> login(loginPassword);
                    default -> throw new ClientException(ClientErrorCode.WRONG_INPUT);
                };

                logger.debug(response.message);
                if (response instanceof LoginDtoResponse) {
                    token = ((LoginDtoResponse) response).token;
                    userInterface.showLogin();
                }
            } catch (ClientException ex) {
                logger.debug(ex.errorMessage);
            }
        }
        return token;
    }

    public BaseDtoResponse registration(final LoginPassword loginPassword) throws ClientException {
        try {
            streamConnector.sendRequest(new RegistrationDtoRequest(
                    loginPassword.login(),
                    loginPassword.password()));
            return streamConnector.getResponse();
        } catch (IOException ex) {
            throw new ClientException(ClientErrorCode.UNKNOWN_IO_EXCEPTION);
        }
    }

    public BaseDtoResponse login(final LoginPassword loginPassword) throws ClientException {
        try {
            streamConnector.sendRequest(new LoginDtoRequest(
                    loginPassword.login(),
                    loginPassword.password()));
            return streamConnector.getResponse();
        } catch (IOException ex) {
            throw new ClientException(ClientErrorCode.UNKNOWN_IO_EXCEPTION);
        }
    }

    public void logout(final String token) throws ClientException {
        try {
            streamConnector.sendRequest(new LogoutDtoRequest(token));
        } catch (IOException ex) {
            throw new ClientException(ClientErrorCode.UNKNOWN_IO_EXCEPTION);
        }
    }
}
