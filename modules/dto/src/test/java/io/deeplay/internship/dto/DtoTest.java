package io.deeplay.internship.dto;

import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DtoTest {
    @Test
    public void checkRegistrationRequest(){
        String login = "Nick";
        String password_hash = "3454465";
        RegistrationDtoRequest registrationDTO = new RegistrationDtoRequest(RequestType.REGISTRATION, login, password_hash);
        assertEquals(registrationDTO.login(), "Nick");
        assertEquals(registrationDTO.passwordHash(), "3454465");
    }

    @Test
    public void checkRegistrationResponse(){
        String message = "Registration was performed!";
        String status = "Success";
        InfoDtoResponse registrationLogoutJoinDTO = new InfoDtoResponse(message, status);
        assertEquals(registrationLogoutJoinDTO.message(), "Registration was performed!");
        assertEquals(registrationLogoutJoinDTO.status(), "Success");
    }

    @Test
    public void checkLoginRequest(){
        String login = "Nick";
        String password_hash = "3454465";
        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(RequestType.LOGIN, login, password_hash);
        assertEquals(loginDtoRequest.login(), "Nick");
        assertEquals(loginDtoRequest.passwordHash(), "3454465");
    }

    @Test
    public void checkLoginResponse(){
        String message = "You are authorized!";
        String status = "Success";
        String token = "89473795";
        LoginDtoResponse registrationLogoutJoinDTO = new LoginDtoResponse(message, status, token);
        assertEquals(registrationLogoutJoinDTO.message(), "You are authorized!");
        assertEquals(registrationLogoutJoinDTO.status(), "Success");
        assertEquals(registrationLogoutJoinDTO.token(), "89473795");
    }

    @Test
    public void checkLogoutRequest(){
        String token = "89473795";
        LogoutDtoRequest logoutDtoRequest = new LogoutDtoRequest(RequestType.LOGOUT, token);
        assertEquals(logoutDtoRequest.token(), "89473795");
    }

    @Test
    public void checkLogoutResponse(){
        String message = "Log out!";
        String status = "Success";
        InfoDtoResponse infoDtoResponse = new InfoDtoResponse(message, status);
        assertEquals(infoDtoResponse.message(), "Log out!");
        assertEquals(infoDtoResponse.status(), "Success");
    }

    @Test
    public void checkCreateGameRequest(){
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
    public void checkCreateGameResponse(){
        String message = "Created!";
        String status = "Success";
        String gameId = "101001930";
        CreateGameDtoResponse infoDtoResponse = new CreateGameDtoResponse(message, status, gameId);
        assertEquals(infoDtoResponse.message(), "Created!");
        assertEquals(infoDtoResponse.status(), "Success");
        assertEquals(infoDtoResponse.gameId(), "101001930");
    }

    @Test
    public void checkJoinGameRequest(){
        String gameId = "101001930";
        String token = "89473795";
        JoinGameDtoRequest joinGameDtoRequest = new JoinGameDtoRequest(RequestType.JOIN_GAME, gameId, token);
        assertEquals(joinGameDtoRequest.token(), "89473795");
        assertEquals(joinGameDtoRequest.gameId(), "101001930");
    }

    @Test
    public void checkJoinGameResponse(){
        String message = "Joined!";
        String status = "Success";
        InfoDtoResponse infoDtoResponse = new InfoDtoResponse(message, status);
        assertEquals(infoDtoResponse.message(), "Joined!");
        assertEquals(infoDtoResponse.status(), "Success");
    }

    @Test
    public void checkTurnRequest(){
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
    public void checkTurnResponse(){
        String message = "Moved!";
        String status = "Success";
        Stone[][] field = new Stone[9][9];
        ActionDtoResponse actionDtoResponse = new ActionDtoResponse(message, status, field);
        assertEquals(actionDtoResponse.message(), "Moved!");
        assertEquals(actionDtoResponse.status(), "Success");
        assertEquals(actionDtoResponse.gameField(), field);
    }

    @Test
    public void checkPassRequest(){
        String token = "89473795";
        PassDtoRequest passDtoRequest = new PassDtoRequest(RequestType.PASS, token);
        assertEquals(passDtoRequest.token(), "89473795");
    }

    @Test
    public void checkPassResponse(){
        String message = "Passed!";
        String status = "Success";
        Stone[][] field = new Stone[9][9];
        ActionDtoResponse actionDtoResponse = new ActionDtoResponse(message, status, field);
        assertEquals(actionDtoResponse.message(), "Passed!");
        assertEquals(actionDtoResponse.status(), "Success");
        assertEquals(actionDtoResponse.gameField(), field);
    }

    @Test
    public void checkSurrenderRequest(){
        String token = "89473795";
        SurrenderDtoRequest surrenderDtoRequest = new SurrenderDtoRequest(RequestType.SURRENDER, token);
        assertEquals(surrenderDtoRequest.token(), "89473795");
    }

    @Test
    public void checkSurrenderResponse(){
        String message = "Surrendered!";
        String status = "Success";
        Stone[][] field = new Stone[9][9];
        ActionDtoResponse actionDtoResponse = new ActionDtoResponse(message, status, field);
        assertEquals(actionDtoResponse.message(), "Surrendered!");
        assertEquals(actionDtoResponse.status(), "Success");
        assertEquals(actionDtoResponse.gameField(), field);
    }

    @Test
    public void checkFinishGameRequest(){
        String token = "89473795";
        SurrenderDtoRequest surrenderDtoRequest = new SurrenderDtoRequest(RequestType.SURRENDER, token);
        assertEquals(surrenderDtoRequest.token(), "89473795");
    }

    @Test
    public void checkFinishGameResponse(){
        String message = "Finished!";
        String status = "Success";
        int whiteScore = 10;
        int blackScore = 15;
        FinishGameDtoResponse finishGameDtoResponse = new FinishGameDtoResponse(message, status, blackScore, whiteScore);
        assertEquals(finishGameDtoResponse.message(), "Finished!");
        assertEquals(finishGameDtoResponse.status(), "Success");
        assertEquals(finishGameDtoResponse.whiteScore(), whiteScore);
        assertEquals(finishGameDtoResponse.blackScore(), blackScore);
    }

    @Test
    public void checkFailureResponse(){
        String message = "There are two players already!";
        String status = "Failure";
        FailureDtoResponse failureDtoResponse = new FailureDtoResponse(message, status);
        assertEquals(failureDtoResponse.message(), "There are two players already!");
        assertEquals(failureDtoResponse.status(), "Failure");
    }
}
