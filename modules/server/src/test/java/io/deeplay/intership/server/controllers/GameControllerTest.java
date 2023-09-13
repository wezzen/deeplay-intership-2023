package io.deeplay.intership.server.controllers;

import io.deeplay.intership.dto.request.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.JoinGameDtoRequest;
import io.deeplay.intership.dto.response.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ServerErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.server.controllers.GameController;
import io.deeplay.intership.service.GameService;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameControllerTest {
    private final GameService gameService = mock(GameService.class);
    private final Validator validator = mock(Validator.class);
    private final int id = 1;
    private final GameController gameController = new GameController(gameService, validator, id);

    @Test
    public void testConstructor() {
        DataCollectionsAggregator collectionsAggregator = new DataCollectionsAggregator();
        final ConcurrentMap<String, GameSession> map = new ConcurrentHashMap<>();
        assertAll(
                () -> assertDoesNotThrow(() -> new GameController(collectionsAggregator, id)),
                () -> assertDoesNotThrow(() -> new GameController(gameService, validator, id))
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

        assertDoesNotThrow(() -> gameController.createGame(mock(CreateGameDtoRequest.class)));
    }

    @Test
    public void testCreateGameFailure() throws ServerException {
        when(gameService.createGame(mock(CreateGameDtoRequest.class)))
                .thenThrow(new ServerException(ServerErrorCode.NOT_AUTHORIZED));

        assertDoesNotThrow(() -> gameController.createGame(mock(CreateGameDtoRequest.class)));
    }

    @Test
    public void testJoinGameSuccess() throws ServerException {
        final String response = "response";

        when(gameService.joinGame(mock(JoinGameDtoRequest.class))).thenReturn(mock(InfoDtoResponse.class));

        assertDoesNotThrow(() -> gameController.joinGame(mock(JoinGameDtoRequest.class)));
    }

    @Test
    public void testJoinGameFailure() throws ServerException {
        final String response = "response";

        when(gameService.joinGame(mock(JoinGameDtoRequest.class)))
                .thenThrow(new ServerException(ServerErrorCode.NOT_AUTHORIZED));

        assertDoesNotThrow(() -> gameController.joinGame(mock(JoinGameDtoRequest.class)));
    }
}
