package io.deeplay.internship.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.dto.response.authorization.LoginDtoResponse;
import io.deeplay.intership.dto.response.game.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.gameplay.FinishGameDtoResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CastToDtoResponseTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public static void initConfig() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @Test
    public void testCastToCreateGameDtoResponse() throws JsonProcessingException {
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final String message = "Good game";
        final String gameId = UUID.randomUUID().toString();

        final CreateGameDtoResponse dtoResponse = new CreateGameDtoResponse(
                status,
                message,
                gameId);

        final String json = mapper.writeValueAsString(dtoResponse);
        final CreateGameDtoResponse result = (CreateGameDtoResponse) mapper.readValue(json, BaseDtoResponse.class);

        assertAll(
                () -> assertEquals(status, result.status),
                () -> assertEquals(message, result.message),
                () -> assertEquals(gameId, result.gameId)
        );
    }

    @Test
    public void testCastToFinishDtoResponse() throws JsonProcessingException {
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final String message = "Good game";
        final int blackScore = 10;
        final int whiteScore = 20;
        final FinishGameDtoResponse dtoResponse = new FinishGameDtoResponse(
                status,
                message,
                blackScore,
                whiteScore);

        final String json = mapper.writeValueAsString(dtoResponse);
        final FinishGameDtoResponse result = (FinishGameDtoResponse) mapper.readValue(json, BaseDtoResponse.class);

        assertAll(
                () -> assertEquals(status, result.status),
                () -> assertEquals(message, result.message),
                () -> assertEquals(blackScore, result.blackScore),
                () -> assertEquals(whiteScore, result.whiteScore)
        );
    }

    @Test
    public void testCastToFailureResponse() throws JsonProcessingException {
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final String message = "Bad request";

        final FailureDtoResponse dtoResponse = new FailureDtoResponse(
                status,
                message);

        final String json = mapper.writeValueAsString(dtoResponse);
        final FailureDtoResponse result = (FailureDtoResponse) mapper.readValue(json, BaseDtoResponse.class);

        assertAll(
                () -> assertEquals(status, result.status),
                () -> assertEquals(message, result.message)
        );
    }

    @Test
    public void testCastToInfoDtoResponse() throws JsonProcessingException {
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final String message = "Bad request";

        final InfoDtoResponse dtoResponse = new InfoDtoResponse(
                status,
                message);

        final String json = mapper.writeValueAsString(dtoResponse);
        final InfoDtoResponse result = (InfoDtoResponse) mapper.readValue(json, BaseDtoResponse.class);

        assertAll(
                () -> assertEquals(status, result.status),
                () -> assertEquals(message, result.message)
        );
    }

    @Test
    public void testCastToLoginDtoResponse() throws JsonProcessingException {
        final ResponseStatus status = ResponseStatus.SUCCESS;
        final String message = "Good request";
        final String token = UUID.randomUUID().toString();

        final LoginDtoResponse dtoResponse = new LoginDtoResponse(
                status,
                message,
                token);

        final String json = mapper.writeValueAsString(dtoResponse);
        final LoginDtoResponse result = (LoginDtoResponse) mapper.readValue(json, BaseDtoResponse.class);

        assertAll(
                () -> assertEquals(status, result.status),
                () -> assertEquals(message, result.message),
                () -> assertEquals(token, result.token)
        );
    }
}
