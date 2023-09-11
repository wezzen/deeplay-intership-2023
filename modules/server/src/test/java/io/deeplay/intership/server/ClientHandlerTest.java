package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.exception.ServerErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientHandlerTest {
    private final Socket socket = mock(Socket.class);
    private final UserController userController = mock(UserController.class);
    private final GameController gameController = mock(GameController.class);
    private final GameplayController gameplayController = mock(GameplayController.class);

    @Test
    public void testConstructors() {
        DataCollectionsAggregator collectionsAggregator = new DataCollectionsAggregator();

        assertAll(
                () -> assertDoesNotThrow(() -> new ClientHandler(socket, collectionsAggregator)),
                () -> assertDoesNotThrow(() -> new ClientHandler(socket, userController, gameController, gameplayController))
        );
    }

    @Test
    public void testDefineCommand1() throws IOException {
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);
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
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);
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
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);
        final FinishGameDtoRequest finishGame = new FinishGameDtoRequest(gameId);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(finishGame))
        );
    }

    @Test
    public void testDefineCommand3() throws IOException {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);
        final RegistrationDtoRequest registration = new RegistrationDtoRequest(login, password);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(registration))
        );
    }

    @Test
    public void testDefineCommand3_Failure() throws IOException {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);
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
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);
        final LoginDtoRequest loginDto = new LoginDtoRequest(login, password);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(loginDto))
        );
    }

    @Test
    public void testDefineCommand4_Failure() throws IOException {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);
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
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);
        final LogoutDtoRequest logout = new LogoutDtoRequest(token);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(logout))
        );
    }

    @Test
    public void testDefineCommand5_Failure() throws IOException {
        final String token = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);
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
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);

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
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);

        final JoinGameDtoRequest joinGame = new JoinGameDtoRequest(gameId, token, color);

        when(gameController.joinGame(mock(JoinGameDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ServerErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(joinGame))
        );
    }

    @Test
    public void testDefineCommand7() throws IOException {
        final String color = Color.WHITE.name();
        final String token = UUID.randomUUID().toString();
        final int row = 1;
        final int column = 3;
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);

        final TurnDtoRequest turn = new TurnDtoRequest(color, row, column, token);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(turn))
        );
    }

    @Test
    public void testDefineCommand7_Failure() throws IOException {
        final String color = Color.WHITE.name();
        final String token = UUID.randomUUID().toString();
        final int row = 1;
        final int column = 3;
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);

        final TurnDtoRequest turn = new TurnDtoRequest(color, row, column, token);

        when(gameplayController.turn(mock(TurnDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ServerErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(turn))
        );
    }

    @Test
    public void testDefineCommand8() throws IOException {
        final String token = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);

        final PassDtoRequest passGame = new PassDtoRequest(token);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(passGame))
        );
    }

    @Test
    public void testDefineCommand8_Failure() throws IOException {
        final String token = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);

        final PassDtoRequest passGame = new PassDtoRequest(token);

        when(gameplayController.pass(mock(PassDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ServerErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(passGame))
        );
    }

    @Test
    public void testDefineCommand9() throws IOException {
        final String token = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);

        final SurrenderDtoRequest surrender = new SurrenderDtoRequest(token);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(surrender))
        );
    }

    @Test
    public void testDefineCommand9_Failure() throws IOException {
        final String token = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);

        final SurrenderDtoRequest surrender = new SurrenderDtoRequest(token);

        when(gameplayController.surrenderGame(mock(SurrenderDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ServerErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(surrender))
        );
    }

    @Test
    public void testDefineCommand10() throws IOException {
        final String gameId = UUID.randomUUID().toString();
        final ClientHandler clientHandler = new ClientHandler(socket, userController, gameController, gameplayController);

        final FinishGameDtoRequest finishGame = new FinishGameDtoRequest(gameId);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(finishGame))
        );
    }
}
