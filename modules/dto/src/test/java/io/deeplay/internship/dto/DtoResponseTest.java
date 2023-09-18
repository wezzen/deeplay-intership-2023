package io.deeplay.internship.dto;

import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.dto.response.authorization.LoginDtoResponse;
import io.deeplay.intership.dto.response.game.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.gameplay.AnswerDtoResponse;
import io.deeplay.intership.dto.response.gameplay.FinishGameDtoResponse;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DtoResponseTest {
    @Test
    public void checkRegistrationResponse() {
        final String message = "Registration was performed!";
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final InfoDtoResponse registrationLogoutJoinDTO = new InfoDtoResponse(status, message);

        assertEquals(registrationLogoutJoinDTO.message, "Registration was performed!");
        assertEquals(registrationLogoutJoinDTO.status, ResponseStatus.SUCCESS);
    }

    @Test
    public void checkLoginResponse() {
        final String message = "You are authorized!";
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final String token = "89473795";
        final LoginDtoResponse registrationLogoutJoinDTO = new LoginDtoResponse(status, message, token);

        assertEquals(registrationLogoutJoinDTO.message, "You are authorized!");
        assertEquals(registrationLogoutJoinDTO.status, ResponseStatus.SUCCESS);
        assertEquals(registrationLogoutJoinDTO.token, "89473795");
    }

    @Test
    public void checkLogoutResponse() {
        final String message = "Log out!";
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final InfoDtoResponse infoDtoResponse = new InfoDtoResponse(status, message);

        assertEquals(infoDtoResponse.message, "Log out!");
        assertEquals(infoDtoResponse.status, ResponseStatus.SUCCESS);
    }


    @Test
    public void checkCreateGameResponse() {
        final String message = "Created!";
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final String gameId = "101001930";
        final CreateGameDtoResponse infoDtoResponse = new CreateGameDtoResponse(status, message, gameId);

        assertEquals(infoDtoResponse.message, "Created!");
        assertEquals(infoDtoResponse.status, ResponseStatus.SUCCESS);
        assertEquals(infoDtoResponse.gameId, "101001930");
    }

    @Test
    public void checkJoinGameResponse() {
        final String message = "Joined!";
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final InfoDtoResponse infoDtoResponse = new InfoDtoResponse(status, message);

        assertEquals(infoDtoResponse.message, "Joined!");
        assertEquals(infoDtoResponse.status, ResponseStatus.SUCCESS);
    }

    @Test
    public void checkTurnResponse() {
        final String message = "Moved!";
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final Stone[][] field = new Stone[9][9];
        final AnswerDtoResponse actionDtoResponse = new AnswerDtoResponse(status, message, field);

        assertEquals(actionDtoResponse.message, "Moved!");
        assertEquals(actionDtoResponse.status, ResponseStatus.SUCCESS);
        assertEquals(actionDtoResponse.gameField, field);
    }

    @Test
    public void checkPassResponse() {
        final String message = "Passed!";
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final Stone[][] field = new Stone[9][9];
        final AnswerDtoResponse actionDtoResponse = new AnswerDtoResponse(status, message, field);

        assertEquals(actionDtoResponse.message, "Passed!");
        assertEquals(actionDtoResponse.status, ResponseStatus.SUCCESS);
        assertEquals(actionDtoResponse.gameField, field);
    }

    @Test
    public void checkSurrenderResponse() {
        final String message = "Surrendered!";
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final Stone[][] field = new Stone[9][9];
        final AnswerDtoResponse actionDtoResponse = new AnswerDtoResponse(status, message, field);

        assertEquals(actionDtoResponse.message, "Surrendered!");
        assertEquals(actionDtoResponse.status, ResponseStatus.SUCCESS);
        assertEquals(actionDtoResponse.gameField, field);
    }

    @Test
    public void checkFinishGameResponse() {
        final String message = "Finished!";
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final int whiteScore = 10;
        final int blackScore = 15;
        final FinishGameDtoResponse finishGameDtoResponse = new FinishGameDtoResponse(status, message, blackScore, whiteScore);

        assertEquals(finishGameDtoResponse.message, "Finished!");
        assertEquals(finishGameDtoResponse.status, ResponseStatus.SUCCESS);
        assertEquals(finishGameDtoResponse.whiteScore, whiteScore);
        assertEquals(finishGameDtoResponse.blackScore, blackScore);
    }

    @Test
    public void checkFailureResponse() {
        final String message = "There are two players already!";
        final ResponseStatus status = ResponseStatus.FAILURE;
        final FailureDtoResponse failureDtoResponse = new FailureDtoResponse(status, message);

        assertEquals(failureDtoResponse.message, "There are two players already!");
        assertEquals(failureDtoResponse.status, ResponseStatus.FAILURE);
    }
}
