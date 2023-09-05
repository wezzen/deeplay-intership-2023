package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.PassDtoRequest;
import io.deeplay.intership.dto.request.SurrenderDtoRequest;
import io.deeplay.intership.dto.request.TurnDtoRequest;
import io.deeplay.intership.dto.response.ActionDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.service.GameplayService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameplayTest {
    private final GameplayService gameplayService = mock(GameplayService.class);
    private final Validator validator = mock(Validator.class);
    private final int id = 1;
    private final GameplayController gameplayController = new GameplayController(gameplayService, validator, id);

    @Test
    public void testSurrenderGameSuccess() throws ServerException {
        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_CREATE_GAME.message);
        final String response = "response";

        when(gameplayService.surrenderGame(mock(SurrenderDtoRequest.class))).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> gameplayController.surrenderGame(mock(SurrenderDtoRequest.class)));
    }

    @Test
    public void testSurrenderGameFailure() throws ServerException {
        final String token = UUID.randomUUID().toString();
        final SurrenderDtoRequest dtoRequest = new SurrenderDtoRequest(token);
        final String response = "response";

        when(gameplayService.surrenderGame(mock(SurrenderDtoRequest.class)))
                .thenThrow(new ServerException(ErrorCode.NOT_AUTHORIZED));

        assertDoesNotThrow(() -> gameplayController.surrenderGame(dtoRequest));
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

        when(gameplayService.turn(mock(TurnDtoRequest.class))).thenReturn(mock(ActionDtoResponse.class));

        assertDoesNotThrow(() -> gameplayController.turn(dtoRequest));
    }

    @Test
    public void testTurnFailure() throws ServerException {
        final String response = "response";

        when(gameplayService.turn(mock(TurnDtoRequest.class)))
                .thenThrow(new ServerException(ErrorCode.NOT_AUTHORIZED));

        assertDoesNotThrow(() -> gameplayController.turn(mock(TurnDtoRequest.class)));
    }

    @Test
    public void testPassSuccess() throws ServerException {
        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_PASS.message);
        final String response = "response";

        when(gameplayService.pass(mock(PassDtoRequest.class))).thenReturn(dtoResponse);

        assertDoesNotThrow(() -> gameplayController.pass(mock(PassDtoRequest.class)));
    }

    @Test
    public void testPassFailure() throws ServerException {
        final String response = "response";

        when(gameplayService.pass(mock(PassDtoRequest.class)))
                .thenThrow(new ServerException(ErrorCode.NOT_AUTHORIZED));

        assertDoesNotThrow(() -> gameplayController.pass(mock(PassDtoRequest.class)));
    }
}
