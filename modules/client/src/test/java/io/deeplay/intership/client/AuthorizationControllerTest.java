package io.deeplay.intership.client;

import io.deeplay.intership.UserInterface;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.LoginDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.exception.ClientException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorizationControllerTest {
    private final StreamConnector streamConnector = mock(StreamConnector.class);
    private final UserInterface userInterface = mock(UserInterface.class);
    private final DecisionMaker decisionMaker = mock(DecisionMaker.class);

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new AuthorizationController(streamConnector, userInterface, decisionMaker));
    }

    @Test
    public void testRegistration() throws ClientException {
        final AuthorizationController authorizationController = new AuthorizationController(streamConnector, userInterface, decisionMaker);
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final LoginPassword loginPassword = new LoginPassword(RequestType.REGISTRATION, login, password);
        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS.text,
                ResponseInfoMessage.SUCCESS_AUTHORIZATION.message);

        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> authorizationController.registration(loginPassword));
    }

    @Test
    public void testLogin() throws ClientException {
        final AuthorizationController authorizationController = new AuthorizationController(streamConnector, userInterface, decisionMaker);
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final LoginPassword loginPassword = new LoginPassword(RequestType.LOGIN, login, password);
        final LoginDtoResponse dtoResponse = new LoginDtoResponse(
                ResponseStatus.SUCCESS.text,
                ResponseInfoMessage.SUCCESS_AUTHORIZATION.message,
                token);

        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> authorizationController.login(loginPassword));
    }

    @Test
    public void testLogout() throws ClientException {
        final AuthorizationController authorizationController = new AuthorizationController(streamConnector, userInterface, decisionMaker);
        final String token = UUID.randomUUID().toString();
        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS.text,
                ResponseInfoMessage.SUCCESS_LOGOUT.message);

        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> authorizationController.logout(token));
    }
}
