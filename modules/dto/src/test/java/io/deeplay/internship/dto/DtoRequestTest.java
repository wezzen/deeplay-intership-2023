package io.deeplay.internship.dto;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.request.authorization.LoginDtoRequest;
import io.deeplay.intership.dto.request.authorization.LogoutDtoRequest;
import io.deeplay.intership.dto.request.authorization.RegistrationDtoRequest;
import io.deeplay.intership.dto.request.game.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.game.JoinGameDtoRequest;
import io.deeplay.intership.dto.request.gameplay.FinishGameDtoRequest;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DtoRequestTest {
    @Test
    public void checkRegistrationRequest() {
        final String login = "Nick";
        final String password_hash = "3454465";
        final RegistrationDtoRequest registrationDTO = new RegistrationDtoRequest(login, password_hash);

        assertEquals(registrationDTO.login, login);
        assertEquals(registrationDTO.passwordHash, password_hash);
    }

    @Test
    public void checkLoginRequest() {
        final String login = "Nick";
        final String password_hash = "3454465";
        final LoginDtoRequest loginDtoRequest = new LoginDtoRequest(login, password_hash);

        assertEquals(loginDtoRequest.login, login);
        assertEquals(loginDtoRequest.passwordHash, password_hash);
    }

    @Test
    public void checkLogoutRequest() {
        final String token = UUID.randomUUID().toString();
        final LogoutDtoRequest logoutDtoRequest = new LogoutDtoRequest(token);

        assertEquals(logoutDtoRequest.token, token);
    }

    @Test
    public void checkCreateGameRequest() {
        final String color = "WHITE";
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final boolean withBot = true;
        final CreateGameDtoRequest createGameDtoRequest = new CreateGameDtoRequest(withBot, color, size, token);

        assertEquals(createGameDtoRequest.token, token);
        assertEquals(createGameDtoRequest.size, size);
        assertEquals(createGameDtoRequest.color, color);
        assertEquals(createGameDtoRequest.withBot, withBot);
    }

    @Test
    public void checkJoinGameRequest() {
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest joinGameDtoRequest = new JoinGameDtoRequest(gameId, token, color);

        assertEquals(joinGameDtoRequest.token, token);
        assertEquals(joinGameDtoRequest.gameId, gameId);
    }

    @Test
    public void checkTurnRequest() {
        final String color = "BLACK";
        final int row = 5;
        final int column = 4;
        final String token = UUID.randomUUID().toString();
        final TurnDtoRequest joinGameDtoRequest = new TurnDtoRequest(color, row, column, token);

        assertEquals(joinGameDtoRequest.token, token);
        assertEquals(joinGameDtoRequest.color, color);
        assertEquals(joinGameDtoRequest.row, row);
        assertEquals(joinGameDtoRequest.column, column);
    }

    @Test
    public void checkPassRequest() {
        final String token = UUID.randomUUID().toString();
        final PassDtoRequest passDtoRequest = new PassDtoRequest(token);

        assertEquals(passDtoRequest.token, token);
    }

    @Test
    public void checkSurrenderRequest() {
        final String token = UUID.randomUUID().toString();
        final SurrenderDtoRequest surrenderDtoRequest = new SurrenderDtoRequest(token);
        assertEquals(surrenderDtoRequest.token, token);
    }

    @Test
    public void checkFinishGameRequest() {
        final String gameId = UUID.randomUUID().toString();
        final FinishGameDtoRequest finishGameDtoRequest = new FinishGameDtoRequest(gameId);
        assertEquals(gameId, finishGameDtoRequest.gameId);
    }

}
