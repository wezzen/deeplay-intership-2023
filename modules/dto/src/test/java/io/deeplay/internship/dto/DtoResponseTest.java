package io.deeplay.internship.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.deeplay.intership.dto.ResponseInfoMessage;
import io.deeplay.intership.dto.ResponseStatus;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DtoResponseTest {
    @Test
    public void checkRegistrationResponse() {
        String message = "Registration was performed!";
        String status = "Success";
        InfoDtoResponse registrationLogoutJoinDTO = new InfoDtoResponse(status, message);
        assertEquals(registrationLogoutJoinDTO.message, "Registration was performed!");
        assertEquals(registrationLogoutJoinDTO.status, "Success");
    }

    @Test
    public void checkLoginResponse() {
        String message = "You are authorized!";
        String status = "Success";
        String token = "89473795";
        LoginDtoResponse registrationLogoutJoinDTO = new LoginDtoResponse(status, message, token);
        assertEquals(registrationLogoutJoinDTO.message, "You are authorized!");
        assertEquals(registrationLogoutJoinDTO.status, "Success");
        assertEquals(registrationLogoutJoinDTO.token, "89473795");
    }

    @Test
    public void checkLogoutResponse() {
        String message = "Log out!";
        String status = "Success";
        InfoDtoResponse infoDtoResponse = new InfoDtoResponse(status, message);
        assertEquals(infoDtoResponse.message, "Log out!");
        assertEquals(infoDtoResponse.status, "Success");
    }


    @Test
    public void checkCreateGameResponse() {
        String message = "Created!";
        String status = "Success";
        String gameId = "101001930";
        CreateGameDtoResponse infoDtoResponse = new CreateGameDtoResponse(status, message, gameId);
        assertEquals(infoDtoResponse.message, "Created!");
        assertEquals(infoDtoResponse.status, "Success");
        assertEquals(infoDtoResponse.gameId, "101001930");
    }

    @Test
    public void checkJoinGameResponse() {
        String message = "Joined!";
        String status = "Success";
        InfoDtoResponse infoDtoResponse = new InfoDtoResponse(status, message);
        assertEquals(infoDtoResponse.message, "Joined!");
        assertEquals(infoDtoResponse.status, "Success");
    }

    @Test
    public void checkTurnResponse() {
        String message = "Moved!";
        String status = "Success";
        Stone[][] field = new Stone[9][9];
        ActionDtoResponse actionDtoResponse = new ActionDtoResponse(status, message, field);
        assertEquals(actionDtoResponse.message, "Moved!");
        assertEquals(actionDtoResponse.status, "Success");
        assertEquals(actionDtoResponse.gameField, field);
    }

    @Test
    public void checkPassResponse() {
        String message = "Passed!";
        String status = "Success";
        Stone[][] field = new Stone[9][9];
        ActionDtoResponse actionDtoResponse = new ActionDtoResponse(status, message, field);
        assertEquals(actionDtoResponse.message, "Passed!");
        assertEquals(actionDtoResponse.status, "Success");
        assertEquals(actionDtoResponse.gameField, field);
    }

    @Test
    public void checkSurrenderResponse() {
        String message = "Surrendered!";
        String status = "Success";
        Stone[][] field = new Stone[9][9];
        ActionDtoResponse actionDtoResponse = new ActionDtoResponse(status, message, field);
        assertEquals(actionDtoResponse.message, "Surrendered!");
        assertEquals(actionDtoResponse.status, "Success");
        assertEquals(actionDtoResponse.gameField, field);
    }

    @Test
    public void checkFinishGameResponse() {
        String message = "Finished!";
        String status = "Success";
        int whiteScore = 10;
        int blackScore = 15;
        FinishGameDtoResponse finishGameDtoResponse = new FinishGameDtoResponse(status, message, blackScore, whiteScore);

        assertEquals(finishGameDtoResponse.message, "Finished!");
        assertEquals(finishGameDtoResponse.status, "Success");
        assertEquals(finishGameDtoResponse.whiteScore, whiteScore);
        assertEquals(finishGameDtoResponse.blackScore, blackScore);
    }

    @Test
    public void checkFailureResponse() {
        String message = "There are two players already!";
        String status = "Failure";
        FailureDtoResponse failureDtoResponse = new FailureDtoResponse(status, message);

        assertEquals(failureDtoResponse.message, "There are two players already!");
        assertEquals(failureDtoResponse.status, "Failure");
    }

    @Test
    public void testCastToActionDtoResponse() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ActionDtoResponse dtoResponse = new ActionDtoResponse(
                ResponseType.ACTION,
                ResponseStatus.SUCCESS.text,
                ResponseInfoMessage.SUCCESS_TURN.message,
                new Stone[][]{});
        final String json = mapper.writeValueAsString(dtoResponse);

        ActionDtoResponse result = (ActionDtoResponse) mapper.readValue(json, BaseDtoResponse.class);
        assertAll(
                () -> assertEquals(ResponseType.ACTION, result.responseType),
                () -> assertEquals(ResponseStatus.SUCCESS.text, result.status),
                () -> assertEquals(ResponseInfoMessage.SUCCESS_TURN.message, result.message)
        );
    }

    @Test
    public void testCastToCreateGameDtoResponse() throws JsonProcessingException {
        final String message = "Good game";
        final String gameId = UUID.randomUUID().toString();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CreateGameDtoResponse dtoResponse = new CreateGameDtoResponse(
                ResponseStatus.SUCCESS.text,
                message,
                gameId);

        final String json = mapper.writeValueAsString(dtoResponse);
        CreateGameDtoResponse result = (CreateGameDtoResponse) mapper.readValue(json, BaseDtoResponse.class);

        assertAll(
                () -> assertEquals(ResponseType.CREATE_GAME, result.responseType),
                () -> assertEquals(ResponseStatus.SUCCESS.text, result.status),
                () -> assertEquals(message, result.message),
                () -> assertEquals(gameId, result.gameId)
        );
    }

    @Test
    public void testCastToFinishDtoResponse() throws JsonProcessingException {
        final String message = "Good game";
        final int blackScore = 10;
        final int whiteScore = 20;
        final ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FinishGameDtoResponse dtoResponse = new FinishGameDtoResponse(
                ResponseStatus.SUCCESS.text,
                message,
                blackScore,
                whiteScore);

        final String json = mapper.writeValueAsString(dtoResponse);
        FinishGameDtoResponse result = (FinishGameDtoResponse) mapper.readValue(json, BaseDtoResponse.class);

        assertAll(
                () -> assertEquals(ResponseType.FINISH_GAME, result.responseType),
                () -> assertEquals(ResponseStatus.SUCCESS.text, result.status),
                () -> assertEquals(message, result.message),
                () -> assertEquals(blackScore, result.blackScore),
                () -> assertEquals(whiteScore, result.whiteScore)
        );
    }

    @Test
    public void testCastToFailureResponse() throws JsonProcessingException {
        final String message = "Bad request";
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FailureDtoResponse dtoResponse = new FailureDtoResponse(
                ResponseStatus.FAILURE.text,
                "Bad request");

        final String json = mapper.writeValueAsString(dtoResponse);
        FailureDtoResponse result = (FailureDtoResponse) mapper.readValue(json, BaseDtoResponse.class);

        assertAll(
                () -> assertEquals(ResponseType.FAILURE, result.responseType),
                () -> assertEquals(ResponseStatus.FAILURE.text, result.status),
                () -> assertEquals(message, result.message)
        );
    }

    @Test
    public void testCastToInfoDtoResponse() throws JsonProcessingException {
        final String status = ResponseStatus.SUCCESS.text;
        final String message = "Bad request";
        final ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InfoDtoResponse dtoResponse = new InfoDtoResponse(
                status,
                message);

        final String json = mapper.writeValueAsString(dtoResponse);
        InfoDtoResponse result = (InfoDtoResponse) mapper.readValue(json, BaseDtoResponse.class);

        assertAll(
                () -> assertEquals(ResponseType.INFO, result.responseType),
                () -> assertEquals(status, result.status),
                () -> assertEquals(message, result.message)
        );
    }

    @Test
    public void testCastToLoginDtoResponse() throws JsonProcessingException {
        final String status = ResponseStatus.SUCCESS.text;
        final String message = "Good request";
        final String token = UUID.randomUUID().toString();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LoginDtoResponse dtoResponse = new LoginDtoResponse(
                status,
                message,
                token);

        final String json = mapper.writeValueAsString(dtoResponse);
        LoginDtoResponse result = (LoginDtoResponse) mapper.readValue(json, BaseDtoResponse.class);

        assertAll(
                () -> assertEquals(ResponseType.LOGIN, result.responseType),
                () -> assertEquals(status, result.status),
                () -> assertEquals(message, result.message),
                () -> assertEquals(token, result.token)
        );
    }
}
