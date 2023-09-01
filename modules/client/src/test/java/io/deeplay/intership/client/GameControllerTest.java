package io.deeplay.intership.client;

import io.deeplay.intership.UserInterface;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.dto.response.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    public void testJoinToGame() throws ClientException {
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
                ResponseStatus.SUCCESS.text,
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message,
                gameId);

        when(decisionMaker.getGameConfig()).thenReturn(gameConfig);
        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> gameController.joinToGame(token));
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
                ResponseStatus.SUCCESS.text,
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message);

        when(decisionMaker.getGameConfig()).thenReturn(gameConfig);
        when(streamConnector.getResponse()).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> gameController.joinToGame(token));
    }

}
