package io.deeplay.intership.server;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.dto.request.authorization.LoginDtoRequest;
import io.deeplay.intership.dto.request.authorization.LogoutDtoRequest;
import io.deeplay.intership.dto.request.authorization.RegistrationDtoRequest;
import io.deeplay.intership.dto.request.game.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.game.JoinGameDtoRequest;
import io.deeplay.intership.dto.request.gameplay.FinishGameDtoRequest;
import io.deeplay.intership.exception.ServerErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.server.controllers.GameController;
import io.deeplay.intership.server.controllers.GameplayController;
import io.deeplay.intership.server.controllers.UserController;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientHandlerTest {
    private static Socket socket;
    private final GameManager gameManager = mock(GameManager.class);
    private final UserController userController = mock(UserController.class);
    private final GameController gameController = mock(GameController.class);
    private final GameplayController gameplayController = mock(GameplayController.class);

    @BeforeAll
    public static void init() throws IOException {
        final int port = 5555;
        final ServerSocket serverSocket = new ServerSocket(port);
        socket = new Socket("localhost", port);
    }

    @Test
    public void testConstructors() {
        final DataCollectionsAggregator collectionsAggregator = new DataCollectionsAggregator();

        assertAll(
                () -> assertDoesNotThrow(() -> new ClientHandler(socket, collectionsAggregator, gameManager)),
                () -> assertDoesNotThrow(() -> new ClientHandler(socket, userController, gameController, gameManager))
        );
    }

    @Test
    public void testDefineCommand1() throws IOException {
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameManager);
        final CreateGameDtoRequest createGame = new CreateGameDtoRequest(
                true,
                color,
                size,
                token);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(createGame))
        );
    }

    @Test
    public void testDefineCommand1_Failure() throws IOException {
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameManager);
        final CreateGameDtoRequest createGame = new CreateGameDtoRequest(
                true,
                color,
                size,
                token);

        when(gameController.createGame(mock(CreateGameDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ServerErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(createGame))
        );
    }

    @Test
    public void testDefineCommand2() throws IOException {
        final String gameId = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameManager);
        final FinishGameDtoRequest finishGame = new FinishGameDtoRequest(gameId);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(finishGame))
        );
    }

    @Test
    public void testDefineCommand3() throws IOException {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameManager);
        final RegistrationDtoRequest registration = new RegistrationDtoRequest(login, password);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(registration))
        );
    }

    @Test
    public void testDefineCommand3_Failure() throws IOException {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameManager);
        final RegistrationDtoRequest registration = new RegistrationDtoRequest(login, password);

        when(userController.registerUser(mock(RegistrationDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ServerErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(registration))
        );
    }

    @Test
    public void testDefineCommand4() throws IOException {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameManager);
        final LoginDtoRequest loginDto = new LoginDtoRequest(login, password);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(loginDto))
        );
    }

    @Test
    public void testDefineCommand4_Failure() throws IOException {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameManager);
        final LoginDtoRequest loginDto = new LoginDtoRequest(login, password);

        when(userController.login(mock(LoginDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ServerErrorCode.SERVER_EXCEPTION);
                });
        clientHandler.defineCommand(loginDto);
        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(loginDto))
        );
    }

    @Test
    public void testDefineCommand5() throws IOException {
        final String token = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameManager);
        final LogoutDtoRequest logout = new LogoutDtoRequest(token);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(logout))
        );
    }

    @Test
    public void testDefineCommand5_Failure() throws IOException {
        final String token = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameManager);
        final LogoutDtoRequest logout = new LogoutDtoRequest(token);

        when(userController.logout(mock(LogoutDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ServerErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(logout))
        );
    }

    @Test
    public void testDefineCommand6() throws IOException {
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameManager);

        final JoinGameDtoRequest joinGame = new JoinGameDtoRequest(gameId, token, color);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(joinGame))
        );
    }

    @Test
    public void testDefineCommand6_Failure() throws IOException {
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameManager);

        final JoinGameDtoRequest joinGame = new JoinGameDtoRequest(gameId, token, color);

        when(gameController.joinGame(mock(JoinGameDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ServerErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(joinGame))
        );
    }
}
