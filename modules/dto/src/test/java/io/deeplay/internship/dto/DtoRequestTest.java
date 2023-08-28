package io.deeplay.internship.dto;

import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DtoRequestTest {
    @Test
    public void checkRegistrationRequest() {
        String login = "Nick";
        String password_hash = "3454465";
        RegistrationDtoRequest registrationDTO = new RegistrationDtoRequest(RequestType.REGISTRATION, login, password_hash);
        assertEquals(registrationDTO.login(), "Nick");
        assertEquals(registrationDTO.passwordHash(), "3454465");
    }

    @Test
    public void checkLoginRequest() {
        String login = "Nick";
        String password_hash = "3454465";
        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(RequestType.LOGIN, login, password_hash);
        assertEquals(loginDtoRequest.login(), "Nick");
        assertEquals(loginDtoRequest.passwordHash(), "3454465");
    }

    @Test
    public void checkLogoutRequest() {
        String token = "89473795";
        LogoutDtoRequest logoutDtoRequest = new LogoutDtoRequest(RequestType.LOGOUT, token);
        assertEquals(logoutDtoRequest.token(), "89473795");
    }

    @Test
    public void checkCreateGameRequest() {
        String color = "WHITE";
        int size = 9;
        String token = "89473795";
        boolean withBot = true;
        CreateGameDtoRequest createGameDtoRequest = new CreateGameDtoRequest(RequestType.CREATE_GAME, withBot, color, size, token);
        assertEquals(createGameDtoRequest.token(), "89473795");
        assertEquals(createGameDtoRequest.size(), 9);
        assertEquals(createGameDtoRequest.color(), "WHITE");
        assertEquals(createGameDtoRequest.withBot(), true);
    }

    @Test
    public void checkJoinGameRequest() {
        String gameId = "101001930";
        String token = "89473795";
        String color = Color.WHITE.name();
        JoinGameDtoRequest joinGameDtoRequest = new JoinGameDtoRequest(RequestType.JOIN_GAME, gameId, token, color);
        assertEquals(joinGameDtoRequest.token(), "89473795");
        assertEquals(joinGameDtoRequest.gameId(), "101001930");
    }

    @Test
    public void checkTurnRequest() {
        String color = "BLACK";
        int row = 5;
        int column = 4;
        String token = "89473795";
        TurnDtoRequest joinGameDtoRequest = new TurnDtoRequest(RequestType.TURN, color, row, column, token);
        assertEquals(joinGameDtoRequest.token(), "89473795");
        assertEquals(joinGameDtoRequest.color(), "BLACK");
        assertEquals(joinGameDtoRequest.row(), 5);
        assertEquals(joinGameDtoRequest.column(), 4);
    }

    @Test
    public void checkPassRequest() {
        String token = "89473795";
        PassDtoRequest passDtoRequest = new PassDtoRequest(RequestType.PASS, token);
        assertEquals(passDtoRequest.token(), "89473795");
    }

    @Test
    public void checkSurrenderRequest() {
        String token = "89473795";
        SurrenderDtoRequest surrenderDtoRequest = new SurrenderDtoRequest(RequestType.SURRENDER, token);
        assertEquals(surrenderDtoRequest.token(), "89473795");
    }

    @Test
    public void checkFinishGameRequest() {
        String token = "89473795";
        SurrenderDtoRequest surrenderDtoRequest = new SurrenderDtoRequest(RequestType.SURRENDER, token);
        assertEquals(surrenderDtoRequest.token(), "89473795");
    }

}
