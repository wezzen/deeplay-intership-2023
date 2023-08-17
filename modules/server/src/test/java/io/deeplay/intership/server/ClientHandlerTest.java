package io.deeplay.intership.server;

import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.dto.request.BaseDto;
import io.deeplay.intership.service.GameService;
import io.deeplay.intership.service.UserService;
import io.deeplay.intership.json.converter.JSONConverter;
import org.junit.jupiter.api.Test;

import java.net.Socket;

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
        final ClientHandler clientHandler1 = new ClientHandler(socket);

        assertAll(
                () -> assertDoesNotThrow(() -> new ClientHandler(socket)),
                () -> assertDoesNotThrow(() -> new ClientHandler(socket, gameService, userService, converter))
        );
    }

    @Test
    public void testDefineCommand1() {
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final RequestType type = RequestType.CREATE_GAME;
        final BaseDto createGame = new BaseDto(type);
        final String request = converter.getJsonFromObject(createGame);
        final BaseDto expected = new BaseDto(type);

        when(converter.getObjectFromJson(request, BaseDto.class)).thenReturn(expected);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand2() {
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final RequestType type = RequestType.FINISH_GAME;
        final BaseDto createGame = new BaseDto(type);
        final String request = converter.getJsonFromObject(createGame);
        final BaseDto expected = new BaseDto(type);

        when(converter.getObjectFromJson(request, BaseDto.class)).thenReturn(expected);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand3() {
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final RequestType type = RequestType.REGISTRATION;
        final BaseDto createGame = new BaseDto(type);
        final String request = converter.getJsonFromObject(createGame);
        final BaseDto expected = new BaseDto(type);

        when(converter.getObjectFromJson(request, BaseDto.class)).thenReturn(expected);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand4() {
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final RequestType type = RequestType.LOGIN;
        final BaseDto createGame = new BaseDto(type);
        final String request = converter.getJsonFromObject(createGame);
        final BaseDto expected = new BaseDto(type);

        when(converter.getObjectFromJson(request, BaseDto.class)).thenReturn(expected);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand5() {
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final RequestType type = RequestType.LOGOUT;
        final BaseDto createGame = new BaseDto(type);
        final String request = converter.getJsonFromObject(createGame);
        final BaseDto expected = new BaseDto(type);

        when(converter.getObjectFromJson(request, BaseDto.class)).thenReturn(expected);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand6() {
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final RequestType type = RequestType.JOIN_GAME;
        final BaseDto createGame = new BaseDto(type);
        final String request = converter.getJsonFromObject(createGame);
        final BaseDto expected = new BaseDto(type);

        when(converter.getObjectFromJson(request, BaseDto.class)).thenReturn(expected);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand7() {
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final RequestType type = RequestType.TURN;
        final BaseDto createGame = new BaseDto(type);
        final String request = converter.getJsonFromObject(createGame);
        final BaseDto expected = new BaseDto(type);

        when(converter.getObjectFromJson(request, BaseDto.class)).thenReturn(expected);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand8() {
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final RequestType type = RequestType.PASS;
        final BaseDto createGame = new BaseDto(type);
        final String request = converter.getJsonFromObject(createGame);
        final BaseDto expected = new BaseDto(type);

        when(converter.getObjectFromJson(request, BaseDto.class)).thenReturn(expected);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand9() {
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final RequestType type = RequestType.SURRENDER;
        final BaseDto createGame = new BaseDto(type);
        final String request = converter.getJsonFromObject(createGame);
        final BaseDto expected = new BaseDto(type);

        when(converter.getObjectFromJson(request, BaseDto.class)).thenReturn(expected);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }

    @Test
    public void testDefineCommand10() {
        final Socket socket = new Socket();
        final ClientHandler clientHandler = new ClientHandler(socket, gameService, userService, converter);
        final RequestType type = RequestType.FINISH_GAME;
        final BaseDto createGame = new BaseDto(type);
        final String request = converter.getJsonFromObject(createGame);
        final BaseDto expected = new BaseDto(type);

        when(converter.getObjectFromJson(request, BaseDto.class)).thenReturn(expected);

        assertAll(
                () -> assertDoesNotThrow(() -> clientHandler.defineCommand(request))
        );
    }
}
