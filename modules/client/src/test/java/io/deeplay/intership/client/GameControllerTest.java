package io.deeplay.intership.client;

import io.deeplay.intership.UserInterface;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameControllerTest {
    private final StreamConnector streamConnector = mock(StreamConnector.class);
    private final UserInterface userInterface = mock(UserInterface.class);
    private final DecisionMaker decisionMaker = mock(DecisionMaker.class);
    private final GameController gameController = new GameController(streamConnector, userInterface, decisionMaker);

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new GameController(streamConnector, userInterface, decisionMaker));
    }

    @Test
    public void testJoinToGame_CreateGame() throws ClientException {
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
    public void testJoinToGame_JoinGame() throws ClientException {
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
    public void testLogin() throws ClientException {
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
    public void testDefineGameAction_ForTurn() throws ClientException {
        final int fieldSize = 9;
        final int row = 1;
        final int column = 5;
        final GameAction gameAction = new GameAction(
                RequestType.TURN,
                row,
                column);
        final ActionDtoResponse dtoResponse = new ActionDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_TURN.message,
                new Stone[fieldSize][fieldSize]);
        gameController.setColor(Color.BLACK);

        when(decisionMaker.getGameAction()).thenReturn(gameAction);
        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        var result = gameController.defineGameAction();
        assertAll(
                () -> assertDoesNotThrow(gameController::defineGameAction),
                () -> assertEquals(ResponseStatus.SUCCESS, result.status),
                () -> assertEquals(ResponseInfoMessage.SUCCESS_TURN.message, result.message)
        );
    }

    @Test
    public void testDefineGameAction_ForPass() throws ClientException {
        final int fieldSize = 9;
        final int row = 1;
        final int column = 5;
        final GameAction gameAction = new GameAction(
                RequestType.PASS,
                row,
                column);
        final ActionDtoResponse dtoResponse = new ActionDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_PASS.message,
                new Stone[fieldSize][fieldSize]);
        gameController.setColor(Color.BLACK);

        when(decisionMaker.getGameAction()).thenReturn(gameAction);
        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        var result = gameController.defineGameAction();
        assertAll(
                () -> assertDoesNotThrow(gameController::defineGameAction),
                () -> assertEquals(ResponseStatus.SUCCESS, result.status),
                () -> assertEquals(ResponseInfoMessage.SUCCESS_PASS.message, result.message)
        );
    }

    @Test
    public void testDefineGameAction_Failure() throws ClientException {
        final int row = 1;
        final int column = 5;
        final GameAction gameAction = new GameAction(
                RequestType.CREATE_GAME,
                row,
                column);

        when(decisionMaker.getGameAction()).thenReturn(gameAction);

        assertThrows(ClientException.class, gameController::defineGameAction);
    }

    @Test
    public void testIsFinishSuccess() {
        final FinishGameDtoResponse dtoResponse = new FinishGameDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_FINISH_GAME.message,
                10,
                20);
        assertTrue(gameController.isFinish(dtoResponse));
    }

    @Test
    public void testIsFinishFailure() {
        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_FINISH_GAME.message);
        assertFalse(gameController.isFinish(dtoResponse));
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
