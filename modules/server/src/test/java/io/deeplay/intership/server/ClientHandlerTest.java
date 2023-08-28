package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.json.converter.JSONConverter;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.service.GameService;
import io.deeplay.intership.service.UserService;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientHandlerTest {
    private final GameService gameService = mock(GameService.class);
    private final UserService userService = mock(UserService.class);
    private final JSONConverter converter = mock(JSONConverter.class);

    @Test
    public void testConstructors() {
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket);

        assertAll(
                () -> assertDoesNotThrow(() -> new ClientHandler(socket)),
                () -> assertDoesNotThrow(() -> new ClientHandler(socket, gameService, userService, converter))
        );
    }

    @Test
    public void testDefineCommand1() {
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final CreateGameDtoRequest createGame = new CreateGameDtoRequest(
                true,
                color,
                size,
                token);
        final String request = converter.getJsonFromObject(createGame);
        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(createGame);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand1_Failure() throws ServerException {
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final CreateGameDtoRequest createGame = new CreateGameDtoRequest(
                true,
                color,
                size,
                token);
        final String request = converter.getJsonFromObject(createGame);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(createGame);
        when(gameService.createGame(mock(CreateGameDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand2() {
        final String gameId = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final FinishGameDtoRequest finishGame = new FinishGameDtoRequest(gameId);
        final String request = converter.getJsonFromObject(finishGame);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(finishGame);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand2_Failure() {
        final String gameId = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final FinishGameDtoRequest finishGame = new FinishGameDtoRequest(gameId);
        final String request = converter.getJsonFromObject(finishGame);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(finishGame);
        when(gameService.finishGame(mock(FinishGameDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand3() {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final RegistrationDtoRequest registration = new RegistrationDtoRequest(login, password);
        final String request = converter.getJsonFromObject(registration);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(registration);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand3_Failure() throws ServerException {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final RegistrationDtoRequest registration = new RegistrationDtoRequest(login, password);
        final String request = converter.getJsonFromObject(registration);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(registration);
        when(userService.register(mock(RegistrationDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand4() {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final LoginDtoRequest loginDto = new LoginDtoRequest(login, password);
        final String request = converter.getJsonFromObject(loginDto);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(loginDto);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand4_Failure() throws ServerException {
        final String login = "Bot" + UUID.randomUUID();
        final String password = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final LoginDtoRequest loginDto = new LoginDtoRequest(login, password);
        final String request = converter.getJsonFromObject(loginDto);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(loginDto);
        when(userService.authorization(mock(LoginDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ErrorCode.SERVER_EXCEPTION);
                });
        clientHandler.login((LoginDtoRequest) loginDto);
        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand5() {
        final String token = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final LogoutDtoRequest logout = new LogoutDtoRequest(token);
        final String request = converter.getJsonFromObject(logout);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(logout);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand5_Failure() throws ServerException {
        final String token = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final LogoutDtoRequest logout = new LogoutDtoRequest(token);
        final String request = converter.getJsonFromObject(logout);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(logout);
        when(userService.logout(mock(LogoutDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand6() {
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);

        final JoinGameDtoRequest joinGame = new JoinGameDtoRequest(gameId, token, color);
        final String request = converter.getJsonFromObject(joinGame);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(joinGame);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand6_Failure() throws ServerException {
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);

        final JoinGameDtoRequest joinGame = new JoinGameDtoRequest(gameId, token, color);
        final String request = converter.getJsonFromObject(joinGame);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(joinGame);
        when(gameService.joinGame(mock(JoinGameDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand7() {
        final String color = Color.WHITE.name();
        final String token = UUID.randomUUID().toString();
        final int row = 1;
        final int column = 3;
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);

        final TurnDtoRequest turn = new TurnDtoRequest(color, row, column, token);
        final String request = converter.getJsonFromObject(turn);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(turn);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand7_Failure() throws ServerException {
        final String color = Color.WHITE.name();
        final String token = UUID.randomUUID().toString();
        final int row = 1;
        final int column = 3;
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);

        final TurnDtoRequest turn = new TurnDtoRequest(color, row, column, token);
        final String request = converter.getJsonFromObject(turn);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(turn);
        when(gameService.turn(mock(TurnDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand8() {
        final String token = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);

        final PassDtoRequest passGame = new PassDtoRequest(token);
        final String request = converter.getJsonFromObject(passGame);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(passGame);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand8_Failure() throws ServerException {
        final String token = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);

        final PassDtoRequest passGame = new PassDtoRequest(token);
        final String request = converter.getJsonFromObject(passGame);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(passGame);
        when(gameService.pass(mock(PassDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand9() {
        final String token = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);

        final SurrenderDtoRequest surrender = new SurrenderDtoRequest(token);
        final String request = converter.getJsonFromObject(surrender);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(surrender);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand9_Failure() {
        final String token = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);

        final SurrenderDtoRequest surrender = new SurrenderDtoRequest(token);
        final String request = converter.getJsonFromObject(surrender);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(surrender);
        when(gameService.surrenderGame(mock(SurrenderDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand10() {
        final String gameId = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);

        final FinishGameDtoRequest finishGame = new FinishGameDtoRequest(gameId);
        final String request = converter.getJsonFromObject(finishGame);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(finishGame);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand10_Failure() {
        final String gameId = UUID.randomUUID().toString();
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);

        final FinishGameDtoRequest finishGame = new FinishGameDtoRequest(gameId);
        final String request = converter.getJsonFromObject(finishGame);

        when(converter.getObjectFromJson(request, BaseDtoRequest.class)).thenReturn(finishGame);
        when(gameService.finishGame(mock(FinishGameDtoRequest.class)))
                .thenAnswer(invocation -> {
                    throw new ServerException(ErrorCode.SERVER_EXCEPTION);
                });

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }
}
