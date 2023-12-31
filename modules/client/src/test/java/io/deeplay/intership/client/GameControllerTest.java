package io.deeplay.intership.client;

import io.deeplay.intership.client.controllers.GameController;
import io.deeplay.intership.client.controllers.GameplayController;
import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.dto.response.game.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.dto.response.gameplay.FinishGameDtoResponse;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.ui.UserInterface;
import io.deeplay.intership.validation.Validation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameControllerTest {
    private final StreamConnector streamConnector = mock(StreamConnector.class);
    private final UserInterface userInterface = mock(UserInterface.class);
    private final DecisionMaker decisionMaker = mock(DecisionMaker.class);
    private Board board = new Board();
    private Validation validation = new Validation(board);
    private final GameController gameController = new GameController(streamConnector, userInterface, decisionMaker);
    private final GameplayController gameplayController = new GameplayController(streamConnector, userInterface, decisionMaker);

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new GameController(streamConnector, userInterface, decisionMaker));
    }

    @Test
    public void testJoinToGame_CreateGame() throws ClientException, IOException {
        final Color color = Color.BLACK;
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final String gameId = UUID.randomUUID().toString();
        final GameConfig gameConfig = new GameConfig(
                RequestType.CREATE_GAME,
                false,
                color,
                size,
                gameId);
        final CreateGameDtoResponse dtoResponse = new CreateGameDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message,
                gameId);

        when(decisionMaker.getGameConfig()).thenReturn(gameConfig);
        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> gameController.joinToGame(token));
    }

    @Test
    public void testJoinToGame_JoinGame() throws ClientException, IOException {
        final Color color = Color.BLACK;
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final String gameId = UUID.randomUUID().toString();
        final GameConfig gameConfig = new GameConfig(
                RequestType.JOIN_GAME,
                false,
                color,
                size,
                gameId);
        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message);

        when(decisionMaker.getGameConfig()).thenReturn(gameConfig);
        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> gameController.joinToGame(token));
    }

    @Test
    public void testJoinToGame_Failure() throws ClientException {
        final Color color = Color.BLACK;
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final String gameId = UUID.randomUUID().toString();
        final GameConfig gameConfig = new GameConfig(
                RequestType.FINISH_GAME,
                false,
                color,
                size,
                gameId);

        when(decisionMaker.getGameConfig()).thenReturn(gameConfig);

        assertThrows(ClientException.class, () -> gameController.joinToGame(token));
    }

    @Test
    public void testLogin() throws ClientException, IOException {
        final Color color = Color.BLACK;
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final String gameId = UUID.randomUUID().toString();
        final GameConfig gameConfig = new GameConfig(
                RequestType.CREATE_GAME,
                false,
                color,
                size,
                gameId);
        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message);

        when(decisionMaker.getGameConfig()).thenReturn(gameConfig);
        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> gameController.joinToGame(token));
    }

    @Test
    public void testIsFinishSuccess() {
        final FinishGameDtoResponse dtoResponse = new FinishGameDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_FINISH_GAME.message,
                10,
                20);
        assertTrue(gameplayController.isFinish(dtoResponse));
    }

    @Test
    public void testIsFinishFailure() {
        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_FINISH_GAME.message);
        assertFalse(gameplayController.isFinish(dtoResponse));
    }

    @Test
    public void testSetToken() {
        final String token = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> gameController.setToken(token));
    }

    @Test
    public void testSetColor() {
        final Color blackColor = Color.BLACK;
        final Color whiteColor = Color.WHITE;
        assertDoesNotThrow(() -> gameController.setColor(blackColor));
        assertDoesNotThrow(() -> gameController.setColor(whiteColor));
    }
}
