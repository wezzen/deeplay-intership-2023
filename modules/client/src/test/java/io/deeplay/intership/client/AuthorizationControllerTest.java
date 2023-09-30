package io.deeplay.intership.client;

import io.deeplay.intership.client.controllers.AuthorizationController;
import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.ui.UserInterface;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.authorization.LoginDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.exception.ClientException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorizationControllerTest {
    private final StreamConnector streamConnector = mock(StreamConnector.class);
    private final UserInterface userInterface = mock(UserInterface.class);
    private final DecisionMaker decisionMaker = mock(DecisionMaker.class);

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new AuthorizationController(streamConnector, userInterface, decisionMaker));
    }

    @Test
    public void testRegistration() throws IOException {
        final AuthorizationController authorizationController = new AuthorizationController(
                streamConnector,
                userInterface,
                decisionMaker);
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final LoginPassword loginPassword = new LoginPassword(RequestType.REGISTRATION, login, password);
        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_AUTHORIZATION.message);

        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> authorizationController.registration(loginPassword));
    }

    @Test
    public void testLogin() throws IOException {
        final AuthorizationController authorizationController = new AuthorizationController(
                streamConnector,
                userInterface,
                decisionMaker);
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final LoginPassword loginPassword = new LoginPassword(RequestType.LOGIN, login, password);
        final LoginDtoResponse dtoResponse = new LoginDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_AUTHORIZATION.message,
                token);

        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> authorizationController.login(loginPassword));
    }

    @Test
    public void testLogout() throws IOException {
        final AuthorizationController authorizationController = new AuthorizationController(
                streamConnector,
                userInterface,
                decisionMaker);
        final String token = UUID.randomUUID().toString();
        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_LOGOUT.message);

        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> authorizationController.logout(token));
    }

    @Test
    public void testAuthorizeClient_ForLogin() throws IOException, ClientException {
        final AuthorizationController authorizationController = new AuthorizationController(
                streamConnector,
                userInterface,
                decisionMaker);
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final LoginPassword loginPassword = new LoginPassword(
                RequestType.LOGIN,
                login,
                password);
        final LoginDtoResponse loginDtoResponse = new LoginDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_AUTHORIZATION.message,
                token);

        when(decisionMaker.getLoginPassword()).thenReturn(loginPassword);
        when(streamConnector.getResponse()).thenReturn(loginDtoResponse);

        assertAll(
                () -> assertDoesNotThrow(authorizationController::authorizeClient),
                () -> assertEquals(token, authorizationController.authorizeClient())
        );
    }

    @SuppressWarnings("serial")
    private class TestException extends RuntimeException {

    }

    @Test
    public void testAuthorizeClient_ForRegistration() throws IOException, ClientException {
        final AuthorizationController authorizationController = new AuthorizationController(
                streamConnector,
                userInterface,
                decisionMaker);
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final LoginPassword loginPassword = new LoginPassword(
                RequestType.REGISTRATION,
                login,
                password);
        final InfoDtoResponse registrationDtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_REGISTRATION.message);

        //Проверка, что за несколько итераций нельзя выйти из цикла авторизации
        when(decisionMaker.getLoginPassword()).thenReturn(loginPassword);
        doReturn(registrationDtoResponse)
                .doReturn(registrationDtoResponse)
                .doReturn(registrationDtoResponse)
                .doReturn(registrationDtoResponse)
                .doThrow(new TestException())
                .when(streamConnector)
                .getResponse();

        try {
            authorizationController.authorizeClient();
        } catch (TestException ex) {

        }
    }

    @Test
    public void testAuthorizeClient_Failure() throws ClientException {
        final AuthorizationController authorizationController = new AuthorizationController(
                streamConnector,
                userInterface,
                decisionMaker);
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final LoginPassword loginPassword = new LoginPassword(
                RequestType.PASS,
                login,
                password);

        when(decisionMaker.getLoginPassword()).thenReturn(loginPassword);
        doThrow(ClientException.class)
                .doThrow(ClientException.class)
                .doThrow(ClientException.class)
                .doThrow(ClientException.class)
                .doThrow(new TestException())
                .when(decisionMaker)
                .getLoginPassword();
        try {
            authorizationController.authorizeClient();
        } catch (TestException ex) {

        }
    }

}
