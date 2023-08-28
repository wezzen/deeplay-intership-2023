package io.deeplay.internship.dto;

import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DtoResponseTest {
    @Test
    public void checkRegistrationResponse() {
        final String message = "Registration was performed!";
        final String status = "Success";
        final InfoDtoResponse registrationLogoutJoinDTO = new InfoDtoResponse(status, message);

        assertEquals(registrationLogoutJoinDTO.message, "Registration was performed!");
        assertEquals(registrationLogoutJoinDTO.status, "Success");
    }

    @Test
    public void checkLoginResponse() {
        final String message = "You are authorized!";
        final String status = "Success";
        final String token = "89473795";
        final LoginDtoResponse registrationLogoutJoinDTO = new LoginDtoResponse(status, message, token);

        assertEquals(registrationLogoutJoinDTO.message, "You are authorized!");
        assertEquals(registrationLogoutJoinDTO.status, "Success");
        assertEquals(registrationLogoutJoinDTO.token, "89473795");
    }

    @Test
    public void checkLogoutResponse() {
        final String message = "Log out!";
        final String status = "Success";
        final InfoDtoResponse infoDtoResponse = new InfoDtoResponse(status, message);

        assertEquals(infoDtoResponse.message, "Log out!");
        assertEquals(infoDtoResponse.status, "Success");
    }


    @Test
    public void checkCreateGameResponse() {
        final String message = "Created!";
        final String status = "Success";
        final String gameId = "101001930";
        final CreateGameDtoResponse infoDtoResponse = new CreateGameDtoResponse(status, message, gameId);

        assertEquals(infoDtoResponse.message, "Created!");
        assertEquals(infoDtoResponse.status, "Success");
        assertEquals(infoDtoResponse.gameId, "101001930");
    }

    @Test
    public void checkJoinGameResponse() {
        final String message = "Joined!";
        final String status = "Success";
        final InfoDtoResponse infoDtoResponse = new InfoDtoResponse(status, message);

        assertEquals(infoDtoResponse.message, "Joined!");
        assertEquals(infoDtoResponse.status, "Success");
    }

    @Test
    public void checkTurnResponse() {
        final String message = "Moved!";
        final String status = "Success";
        final Stone[][] field = new Stone[9][9];
        final ActionDtoResponse actionDtoResponse = new ActionDtoResponse(status, message, field);

        assertEquals(actionDtoResponse.message, "Moved!");
        assertEquals(actionDtoResponse.status, "Success");
        assertEquals(actionDtoResponse.gameField, field);
    }

    @Test
    public void checkPassResponse() {
        final String message = "Passed!";
        final String status = "Success";
        final Stone[][] field = new Stone[9][9];
        final ActionDtoResponse actionDtoResponse = new ActionDtoResponse(status, message, field);

        assertEquals(actionDtoResponse.message, "Passed!");
        assertEquals(actionDtoResponse.status, "Success");
        assertEquals(actionDtoResponse.gameField, field);
    }

    @Test
    public void checkSurrenderResponse() {
        final String message = "Surrendered!";
        final String status = "Success";
        final Stone[][] field = new Stone[9][9];
        final ActionDtoResponse actionDtoResponse = new ActionDtoResponse(status, message, field);

        assertEquals(actionDtoResponse.message, "Surrendered!");
        assertEquals(actionDtoResponse.status, "Success");
        assertEquals(actionDtoResponse.gameField, field);
    }

    @Test
    public void checkFinishGameResponse() {
        final String message = "Finished!";
        final String status = "Success";
        final int whiteScore = 10;
        final int blackScore = 15;
        final FinishGameDtoResponse finishGameDtoResponse = new FinishGameDtoResponse(status, message, blackScore, whiteScore);

        assertEquals(finishGameDtoResponse.message, "Finished!");
        assertEquals(finishGameDtoResponse.status, "Success");
        assertEquals(finishGameDtoResponse.whiteScore, whiteScore);
        assertEquals(finishGameDtoResponse.blackScore, blackScore);
    }

    @Test
    public void checkFailureResponse() {
        final String message = "There are two players already!";
        final String status = "Failure";
        final FailureDtoResponse failureDtoResponse = new FailureDtoResponse(status, message);

        assertEquals(failureDtoResponse.message, "There are two players already!");
        assertEquals(failureDtoResponse.status, "Failure");
    }
}
