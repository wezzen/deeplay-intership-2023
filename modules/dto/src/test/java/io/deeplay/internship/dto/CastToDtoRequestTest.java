package io.deeplay.internship.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CastToDtoRequestTest {
    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public static void initConfig() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @Test
    public void testCastToCreateGameDtoRequest() throws JsonProcessingException {
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoResponse = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);
        final String json = mapper.writeValueAsString(dtoResponse);

        final CreateGameDtoRequest result = (CreateGameDtoRequest) mapper.readValue(json, BaseDtoRequest.class);
        assertAll(
                () -> assertEquals(withBot, result.withBot),
                () -> assertEquals(color, result.color),
                () -> assertEquals(size, result.size),
                () -> assertEquals(token, result.token)
        );
    }

    @Test
    public void testCastToFinishGameDtoRequest() throws JsonProcessingException {
        final String gameId = UUID.randomUUID().toString();
        final FinishGameDtoRequest dtoResponse = new FinishGameDtoRequest(gameId);
        final String json = mapper.writeValueAsString(dtoResponse);

        final CreateGameDtoRequest result = (CreateGameDtoRequest) mapper.readValue(json, BaseDtoRequest.class);
        assertAll(
                () -> assertEquals(gameId, result.token)
        );
    }

    @Test
    public void testCastToJoinGameDtoRequest() throws JsonProcessingException {
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoResponse = new JoinGameDtoRequest(
                gameId,
                token,
                color);
        final String json = mapper.writeValueAsString(dtoResponse);

        final JoinGameDtoRequest result = (JoinGameDtoRequest) mapper.readValue(json, BaseDtoRequest.class);
        assertAll(
                () -> assertEquals(color, result.color),
                () -> assertEquals(token, result.token),
                () -> assertEquals(gameId, result.gameId)
        );
    }


    @Test
    public void testCastToLoginDtoRequest() throws JsonProcessingException {
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final LoginDtoRequest dtoResponse = new LoginDtoRequest(
                login,
                password);
        final String json = mapper.writeValueAsString(dtoResponse);

        final LoginDtoRequest result = (LoginDtoRequest) mapper.readValue(json, BaseDtoRequest.class);
        assertAll(
                () -> assertEquals(login, result.login),
                () -> assertEquals(password, result.passwordHash)
        );
    }

    @Test
    public void testCastToLogoutDtoRequest() throws JsonProcessingException {
        final String token = UUID.randomUUID().toString();
        final LogoutDtoRequest dtoResponse = new LogoutDtoRequest(token);
        final String json = mapper.writeValueAsString(dtoResponse);

        final LogoutDtoRequest result = (LogoutDtoRequest) mapper.readValue(json, BaseDtoRequest.class);
        assertAll(
                () -> assertEquals(token, result.token)
        );
    }

    @Test
    public void testCastToPassDtoRequest() throws JsonProcessingException {
        final String token = UUID.randomUUID().toString();
        final PassDtoRequest dtoResponse = new PassDtoRequest(token);
        final String json = mapper.writeValueAsString(dtoResponse);

        final PassDtoRequest result = (PassDtoRequest) mapper.readValue(json, BaseDtoRequest.class);
        assertAll(
                () -> assertEquals(token, result.token)
        );
    }

    @Test
    public void testCastToRegistrationDtoRequest() throws JsonProcessingException {
        final String login = Color.WHITE.name();
        final String password = UUID.randomUUID().toString();
        final RegistrationDtoRequest dtoResponse = new RegistrationDtoRequest(
                login,
                password);
        final String json = mapper.writeValueAsString(dtoResponse);

        final RegistrationDtoRequest result = (RegistrationDtoRequest) mapper.readValue(json, BaseDtoRequest.class);
        assertAll(
                () -> assertEquals(login, result.login),
                () -> assertEquals(password, result.passwordHash)
        );
    }

    @Test
    public void testCastToSurrenderDtoRequest() throws JsonProcessingException {
        final String token = UUID.randomUUID().toString();
        final SurrenderDtoRequest dtoResponse = new SurrenderDtoRequest(token);
        final String json = mapper.writeValueAsString(dtoResponse);

        final SurrenderDtoRequest result = (SurrenderDtoRequest) mapper.readValue(json, BaseDtoRequest.class);
        assertAll(
                () -> assertEquals(token, result.token)
        );
    }

    @Test
    public void testCastToTurnDtoRequest() throws JsonProcessingException {
        final String color = Color.WHITE.name();
        final int row = 1;
        final int column = 5;
        final String token = UUID.randomUUID().toString();
        final TurnDtoRequest dtoResponse = new TurnDtoRequest(
                color,
                row,
                column,
                token);
        final String json = mapper.writeValueAsString(dtoResponse);

        final TurnDtoRequest result = (TurnDtoRequest) mapper.readValue(json, BaseDtoRequest.class);
        assertAll(
                () -> assertEquals(color, result.color),
                () -> assertEquals(row, result.row),
                () -> assertEquals(column, result.column),
                () -> assertEquals(token, result.token)
        );
    }

}
