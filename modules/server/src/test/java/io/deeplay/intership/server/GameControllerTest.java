package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.json.converter.JSONConverter;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.service.GameService;
import io.deeplay.intership.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameControllerTest {
    private final UserService userService = mock(UserService.class);
    private final GameService gameService = mock(GameService.class);
    private final JSONConverter jsonConverter = mock(JSONConverter.class);
    private final int id = 1;
    private final GameController gameController = new GameController(userService, gameService, jsonConverter, id);

    @Test
    public void testConstructor() {
        assertAll(
                () -> assertDoesNotThrow(() -> new GameController(id)),
                () -> assertDoesNotThrow(() -> new GameController(userService, gameService, jsonConverter, id))
        );
    }

    @Test
    public void testCreateGameSuccess() throws ServerException {
        final String gameId = UUID.randomUUID().toString();
        final CreateGameDtoResponse dtoResponse = new CreateGameDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message,
                gameId);
        final String response = "response";

        when(gameService.createGame(mock(CreateGameDtoRequest.class))).thenReturn(dtoResponse);
        when(jsonConverter.getJsonFromObject(mock(CreateGameDtoResponse.class))).thenReturn(response);

        assertDoesNotThrow(() -> gameController.createGame(mock(CreateGameDtoRequest.class)));
    }

    @Test
    public void testCreateGameFailure() throws ServerException {
        final String response = "response";

        when(gameService.createGame(mock(CreateGameDtoRequest.class)))
                .thenThrow(new ServerException(ErrorCode.NOT_AUTHORIZED));
        when(jsonConverter.getJsonFromObject(mock(CreateGameDtoResponse.class))).thenReturn(response);

        assertDoesNotThrow(() -> gameController.createGame(mock(CreateGameDtoRequest.class)));
    }

    @Test
    public void testJoinGameSuccess() throws ServerException {
        final String response = "response";

        when(gameService.joinGame(mock(JoinGameDtoRequest.class))).thenReturn(mock(InfoDtoResponse.class));
        when(jsonConverter.getJsonFromObject(mock(InfoDtoResponse.class))).thenReturn(response);

        assertDoesNotThrow(() -> gameController.joinGame(mock(JoinGameDtoRequest.class)));
    }

    @Test
    public void testJoinGameFailure() throws ServerException {
        final String response = "response";

        when(gameService.joinGame(mock(JoinGameDtoRequest.class)))
                .thenThrow(new ServerException(ErrorCode.NOT_AUTHORIZED));
        when(jsonConverter.getJsonFromObject(mock(InfoDtoResponse.class))).thenReturn(response);

        assertDoesNotThrow(() -> gameController.joinGame(mock(JoinGameDtoRequest.class)));
    }

    @Test
    public void testSurrenderGameSuccess() throws ServerException {
        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message);
        final String response = "response";

        when(gameService.surrenderGame(mock(SurrenderDtoRequest.class))).thenReturn(dtoResponse);
        when(jsonConverter.getJsonFromObject(mock(InfoDtoResponse.class))).thenReturn(response);

        assertDoesNotThrow(() -> gameController.surrenderGame(mock(SurrenderDtoRequest.class)));
    }

    @Test
    public void testSurrenderGameFailure() throws ServerException {
        final String token = UUID.randomUUID().toString();
        final SurrenderDtoRequest dtoRequest = new SurrenderDtoRequest(token);
        final String response = "response";

        when(gameService.surrenderGame(mock(SurrenderDtoRequest.class)))
                .thenThrow(new ServerException(ErrorCode.NOT_AUTHORIZED));
        when(jsonConverter.getJsonFromObject(mock(InfoDtoResponse.class))).thenReturn(response);

        assertDoesNotThrow(() -> gameController.surrenderGame(dtoRequest));
    }

    @Test
    public void testTurnSuccess() throws ServerException {
        final String color = Color.WHITE.name();
        final int row = 1;
        final int column = 2;
        final String token = UUID.randomUUID().toString();
        final TurnDtoRequest dtoRequest = new TurnDtoRequest(
                color,
                row,
                column,
                token);
        final String response = "response";

        when(gameService.turn(mock(TurnDtoRequest.class))).thenReturn(mock(ActionDtoResponse.class));
        when(jsonConverter.getJsonFromObject(mock(BaseDtoResponse.class))).thenReturn(response);

        assertDoesNotThrow(() -> gameController.turn(dtoRequest));
    }

    @Test
    public void testTurnFailure() throws ServerException {
        final String response = "response";

        when(gameService.turn(mock(TurnDtoRequest.class)))
                .thenThrow(new ServerException(ErrorCode.NOT_AUTHORIZED));
        when(jsonConverter.getJsonFromObject(mock(InfoDtoResponse.class))).thenReturn(response);

        assertDoesNotThrow(() -> gameController.turn(mock(TurnDtoRequest.class)));
    }

    @Test
    public void testPassSuccess() throws ServerException {
        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_PASS.message);
        final String response = "response";

        when(gameService.pass(mock(PassDtoRequest.class))).thenReturn(dtoResponse);
        when(jsonConverter.getJsonFromObject(mock(BaseDtoResponse.class))).thenReturn(response);

        assertDoesNotThrow(() -> gameController.pass(mock(PassDtoRequest.class)));
    }

    @Test
    public void testPassFailure() throws ServerException {
        final String response = "response";

        when(gameService.pass(mock(PassDtoRequest.class)))
                .thenThrow(new ServerException(ErrorCode.NOT_AUTHORIZED));
        when(jsonConverter.getJsonFromObject(mock(InfoDtoResponse.class))).thenReturn(response);

        assertDoesNotThrow(() -> gameController.pass(mock(PassDtoRequest.class)));
    }
}
