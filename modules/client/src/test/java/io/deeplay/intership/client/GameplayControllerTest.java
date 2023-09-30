package io.deeplay.intership.client;

import io.deeplay.intership.client.controllers.GameplayController;
import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.dto.request.BaseDtoRequest;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.dto.request.gameplay.AnswerDtoType;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.FailureDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.dto.response.gameplay.AnswerDtoResponse;
import io.deeplay.intership.dto.response.gameplay.FinishGameDtoResponse;
import io.deeplay.intership.dto.response.gameplay.StartGameDtoResponse;
import io.deeplay.intership.dto.response.gameplay.UpdateFieldDtoResponse;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.ui.UserInterface;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameplayControllerTest {
    private final StreamConnector streamConnector = mock(StreamConnector.class);
    private final UserInterface userInterface = mock(UserInterface.class);
    private final DecisionMaker decisionMaker = mock(DecisionMaker.class);

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new GameplayController(streamConnector, userInterface, decisionMaker));
    }

    @Test
    public void testIsFinish() {
        final int boardSize = 9;
        final GameplayController gameplayController = new GameplayController(streamConnector, userInterface, decisionMaker);
        final FinishGameDtoResponse finishGameDtoResponse = new FinishGameDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_FINISH_GAME.message,
                1.5,
                0.5);
        final AnswerDtoResponse answerDtoResponse = new AnswerDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_FINISH_GAME.message,
                new Stone[boardSize][boardSize]);
        final UpdateFieldDtoResponse updateFieldDtoResponse = new UpdateFieldDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.CAN_TURN.message,
                new Stone[boardSize][boardSize]);
        final StartGameDtoResponse startGameDtoResponse = new StartGameDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.CAN_TURN.message,
                new Stone[boardSize][boardSize]);
        final FailureDtoResponse failureDtoResponse = new FailureDtoResponse(ResponseStatus.FAILURE, "");

        assertAll(
                () -> assertTrue(gameplayController.isFinish(finishGameDtoResponse)),
                () -> assertFalse(gameplayController.isFinish(answerDtoResponse)),
                () -> assertFalse(gameplayController.isFinish(updateFieldDtoResponse)),
                () -> assertFalse(gameplayController.isFinish(startGameDtoResponse)),
                () -> assertFalse(gameplayController.isFinish(failureDtoResponse))
        );
    }

    @Test
    public void testTurn() {
        final int row = 0;
        final int column = 9;
        final GameAction gameAction = new GameAction(RequestType.TURN, row, column);
        final GameplayController gameplayController = new GameplayController(streamConnector, userInterface, decisionMaker);

        final var res = gameplayController.turn(gameAction);
        assertAll(
                () -> assertEquals(AnswerDtoType.TURN, res.answerType),
                () -> assertEquals(row, res.row),
                () -> assertEquals(column, res.column)
        );
    }

    @Test
    public void testPass() {
        final int row = -1;
        final int column = -1;
        final GameAction gameAction = new GameAction(RequestType.PASS, row, column);
        final GameplayController gameplayController = new GameplayController(streamConnector, userInterface, decisionMaker);

        final var res = gameplayController.turn(gameAction);
        assertAll(
                () -> assertEquals(AnswerDtoType.TURN, res.answerType),
                () -> assertEquals(row, res.row),
                () -> assertEquals(column, res.column)
        );
    }

    @Test
    public void testGetGameActionPass() throws ClientException {
        final int row = -1;
        final int column = -1;
        final GameAction gameAction = new GameAction(RequestType.PASS, row, column);
        final GameplayController gameplayController = new GameplayController(streamConnector, userInterface, decisionMaker);

        when(decisionMaker.getGameAction()).thenReturn(gameAction);

        final BaseDtoRequest res = gameplayController.getGameAction();
        assertNotNull(res);
    }

    @Test
    public void testGetGameActionTurn() throws ClientException {
        final int row = 0;
        final int column = 9;
        final GameAction gameAction = new GameAction(RequestType.TURN, row, column);
        final GameplayController gameplayController = new GameplayController(streamConnector, userInterface, decisionMaker);

        when(decisionMaker.getGameAction()).thenReturn(gameAction);
        final BaseDtoRequest res = gameplayController.getGameAction();
        assertNotNull(res);
    }

    @Test
    public void testGetGameActionFailure() {
        final int row = 0;
        final int column = 9;
        final GameAction gameAction = new GameAction(RequestType.REGISTRATION, row, column);
        final GameplayController gameplayController = new GameplayController(streamConnector, userInterface, decisionMaker);

        when(decisionMaker.getGameAction()).thenReturn(gameAction);
        assertThrows(ClientException.class, gameplayController::getGameAction);
    }

    @Test
    public void testDefineAction() {
        final int boardSize = 9;
        final FinishGameDtoResponse finishGameDtoResponse = new FinishGameDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_FINISH_GAME.message,
                1.5,
                0.5);
        final UpdateFieldDtoResponse updateFieldDtoResponse = new UpdateFieldDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.CAN_TURN.message,
                new Stone[boardSize][boardSize]);
        final StartGameDtoResponse startGameDtoResponse = new StartGameDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.CAN_TURN.message,
                new Stone[boardSize][boardSize]);
        final FailureDtoResponse failureDtoResponse = new FailureDtoResponse(ResponseStatus.FAILURE, "");
        final BaseDtoResponse baseDtoResponse = new BaseDtoResponse(ResponseStatus.SUCCESS, "");

        final GameplayController gameplayController = new GameplayController(streamConnector, userInterface, decisionMaker);
        assertAll(
                () -> assertDoesNotThrow(() -> gameplayController.defineAction(finishGameDtoResponse)),
                () -> assertDoesNotThrow(() -> gameplayController.defineAction(updateFieldDtoResponse)),
                () -> assertDoesNotThrow(() -> gameplayController.defineAction(startGameDtoResponse)),
                () -> assertDoesNotThrow(() -> gameplayController.defineAction(failureDtoResponse)),
                () -> assertDoesNotThrow(() -> gameplayController.defineAction(baseDtoResponse))
        );
    }
}
