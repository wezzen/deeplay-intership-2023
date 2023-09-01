package io.deeplay.intership.client;

import io.deeplay.intership.UserInterface;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.LogoutDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.LoginDtoResponse;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.json.converter.JSONConverter;
import org.apache.log4j.Logger;

public class AuthorizationController {
    private final Logger logger = Logger.getLogger(AuthorizationController.class);
    private final JSONConverter jsonConverter = new JSONConverter();
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
        String request = jsonConverter.getJsonFromObject(new RegistrationDtoRequest(
                loginPassword.login(),
                loginPassword.password()));
        streamConnector.sendRequest(request);
        return streamConnector.getResponse();
    }

    public BaseDtoResponse login(final LoginPassword loginPassword) throws ClientException {
        String requestString = jsonConverter.getJsonFromObject(new LoginDtoRequest(
                loginPassword.login(),
                loginPassword.password()));
        streamConnector.sendRequest(requestString);
        return streamConnector.getResponse();
    }

    public void logout(final String token) throws ClientException {
        String requestString = jsonConverter.getJsonFromObject(new LogoutDtoRequest(token));
        streamConnector.sendRequest(requestString);
    }
}
