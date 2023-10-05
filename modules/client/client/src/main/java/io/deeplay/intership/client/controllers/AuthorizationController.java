package io.deeplay.intership.client.controllers;

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

/**
 * Этот класс обеспечивает взаимодействие клиентов с сервером в процессе авторизации, регистрации и выхода.
 */
public class AuthorizationController extends Controller {
    private final Logger logger = Logger.getLogger(AuthorizationController.class);

    public AuthorizationController(
            final StreamConnector streamConnector,
            final UserInterface userInterface,
            final DecisionMaker decisionMaker) {
        super(streamConnector, userInterface, decisionMaker);
    }

    /**
     * Метод для авторизации клиента.
     *
     * @return Токен клиента после успешной авторизации.
     */
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

    /**
     * Метод для отправки запроса на регистрацию клиента на сервере.
     *
     * @param loginPassword Объект {@link LoginPassword} с данными (логин и пароль) для регистрации.
     * @return Ответ от сервера после попытки регистрации.
     * @throws ClientException Если возникла ошибка взаимодействия с сервером или неверные данные.
     */
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

    /**
     * Метод для отправки запроса на вход клиента на сервере.
     *
     * @param loginPassword Объект {@link LoginPassword} с данными (логин и пароль) для входа.
     * @return Ответ от сервера после попытки входа.
     * @throws ClientException Если возникла ошибка взаимодействия с сервером или неверные данные.
     */
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

    /**
     * Метод для отправки запроса на выход клиента из системы.
     *
     * @param token Токен клиента, который должен разлогиниться.
     * @throws ClientException Если возникла ошибка взаимодействия с сервером.
     */
    public void logout(final String token) throws ClientException {
        try {
            streamConnector.sendRequest(new LogoutDtoRequest(token));
        } catch (IOException ex) {
            throw new ClientException(ClientErrorCode.UNKNOWN_IO_EXCEPTION);
        }
    }
}
