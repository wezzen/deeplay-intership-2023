package io.deeplay.intership.service;

import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.ActionDtoResponse;
import io.deeplay.intership.dto.response.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.LoginDtoResponse;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {
    private static final GameService gameService = new GameService();
    private static final UserService userService = new UserService();
    private static final String login = UUID.randomUUID().toString();
    private static final String passwordHash = UUID.randomUUID().toString();
    private static String token;

    @BeforeAll
    public static void initRegistration() {
        final RegistrationDtoRequest register = new RegistrationDtoRequest(
                login,
                passwordHash);
        try {
            userService.register(register);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void initUserAuth() {
        final LoginDtoRequest loginRequest = new LoginDtoRequest(
                login,
                passwordHash);
        LoginDtoResponse response = null;
        try {
            response = userService.authorization(loginRequest);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
        token = response.token;
    }

    @AfterEach
    public void initUserLogout() {
        final LogoutDtoRequest logoutRequest = new LogoutDtoRequest(token);
        try {
            userService.logout(logoutRequest);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new GameService());
    }

    @Test
    public void testSuccessCreateGame() throws ServerException {
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 9;

        final CreateGameDtoRequest dto = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        final String expectedMessage = ResponseInfoMessage.SUCCESS_CREATE_GAME.message;
        final String expectedStatus = ResponseStatus.SUCCESS.text;
        final CreateGameDtoResponse response = gameService.createGame(dto);
        assertAll(
                () -> assertEquals(expectedMessage, response.message),
                () -> assertEquals(expectedStatus, response.status),
                () -> assertNotNull(response.gameId)
        );
    }

    @Test
    public void testFailureCreateGame_WithBadToken() {
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dto = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertThrows(ServerException.class, () -> gameService.createGame(dto));
    }

    @Test
    public void testSuccessJoinGame() throws ServerException {
        final String firstLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerFirst = new RegistrationDtoRequest(
                firstLogin,
                passwordHash);
        userService.register(registerFirst);
        final LoginDtoRequest loginFirstRequest = new LoginDtoRequest(
                firstLogin,
                passwordHash);
        LoginDtoResponse responseFirst = userService.authorization(loginFirstRequest);
        String firstToken = responseFirst.token;

        final String secondLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerSecond = new RegistrationDtoRequest(
                secondLogin,
                passwordHash);
        userService.register(registerSecond);
        final LoginDtoRequest loginSecondRequest = new LoginDtoRequest(
                secondLogin,
                passwordHash);
        LoginDtoResponse responseSecond = userService.authorization(loginSecondRequest);
        String secondToken = responseSecond.token;

        final boolean withBot = false;
        final String blackColor = Color.BLACK.name();
        final int size = 9;
        final CreateGameDtoRequest createGameRequest = new CreateGameDtoRequest(
                withBot,
                blackColor,
                size,
                firstToken);

        final var gameDto = gameService.createGame(createGameRequest);
        assertAll(
                () -> assertEquals(ResponseInfoMessage.SUCCESS_CREATE_GAME.message, gameDto.message),
                () -> assertEquals(ResponseStatus.SUCCESS.text, gameDto.status),
                () -> assertNotNull(gameDto.gameId)
        );

        final String color = Color.WHITE.name();
        final JoinGameDtoRequest joinGameRequest = new JoinGameDtoRequest(
                gameDto.gameId,
                secondToken,
                color);

        final var dtoResponse = gameService.joinGame(joinGameRequest);
        final String expectedMessage = ResponseInfoMessage.SUCCESS_JOIN_GAME.message;
        final String expectedStatus = ResponseStatus.SUCCESS.text;
        assertAll(
                () -> assertEquals(expectedMessage, dtoResponse.message),
                () -> assertEquals(expectedStatus, dtoResponse.status)
        );
    }

    @Test
    public void testSuccessJoinGame_withEmptyColor() throws ServerException {
        final String firstLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerFirst = new RegistrationDtoRequest(
                firstLogin,
                passwordHash);
        userService.register(registerFirst);
        final LoginDtoRequest loginFirstRequest = new LoginDtoRequest(
                firstLogin,
                passwordHash);
        LoginDtoResponse responseFirst = userService.authorization(loginFirstRequest);
        String firstToken = responseFirst.token;

        final String secondLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerSecond = new RegistrationDtoRequest(
                secondLogin,
                passwordHash);
        userService.register(registerSecond);
        final LoginDtoRequest loginSecondRequest = new LoginDtoRequest(
                secondLogin,
                passwordHash);
        LoginDtoResponse responseSecond = userService.authorization(loginSecondRequest);
        String secondToken = responseSecond.token;

        final boolean withBot = false;
        final String blackColor = Color.EMPTY.name();
        final int size = 9;
        final CreateGameDtoRequest createGameRequest = new CreateGameDtoRequest(
                withBot,
                blackColor,
                size,
                firstToken);

        final var gameDto = gameService.createGame(createGameRequest);
        assertAll(
                () -> assertEquals(ResponseInfoMessage.SUCCESS_CREATE_GAME.message, gameDto.message),
                () -> assertEquals(ResponseStatus.SUCCESS.text, gameDto.status),
                () -> assertNotNull(gameDto.gameId)
        );

        final String color = Color.WHITE.name();
        final JoinGameDtoRequest joinGameRequest = new JoinGameDtoRequest(
                gameDto.gameId,
                secondToken,
                color);

        final var dtoResponse = gameService.joinGame(joinGameRequest);
        final String expectedMessage = ResponseInfoMessage.SUCCESS_JOIN_GAME.message;
        final String expectedStatus = ResponseStatus.SUCCESS.text;
        assertAll(
                () -> assertEquals(expectedMessage, dtoResponse.message),
                () -> assertEquals(expectedStatus, dtoResponse.status)
        );
    }

    @Test
    public void testFailureJoinGame_withConnectCreator() throws ServerException {
        final boolean withBot = false;
        final String whiteColor = Color.WHITE.name();
        final int size = 9;
        final CreateGameDtoRequest createGameRequest = new CreateGameDtoRequest(
                withBot,
                whiteColor,
                size,
                token);
        gameService.createGame(createGameRequest);

        final String color = Color.BLACK.name();
        final String gameId = UUID.randomUUID().toString();
        final JoinGameDtoRequest joinGameRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> gameService.joinGame(joinGameRequest));
    }

    @Test
    public void testFailureJoinGame_withBadToken() {
        final String color = Color.WHITE.name();
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final JoinGameDtoRequest dto = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> gameService.joinGame(dto));
    }

    @Test
    public void testSurrenderGame() {
        final String token = UUID.randomUUID().toString();
        final SurrenderDtoRequest dto = new SurrenderDtoRequest(token);

        assertDoesNotThrow(() -> gameService.surrenderGame(dto));
    }

    @Test
    public void testFinishGame() {
        final String gameId = UUID.randomUUID().toString();
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(gameId);
        assertDoesNotThrow(() -> gameService.finishGame(dtoRequest));
    }

    @Test
    public void testSuccessTurn() throws ServerException {
        final String firstLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerFirst = new RegistrationDtoRequest(
                firstLogin,
                passwordHash);
        userService.register(registerFirst);
        final LoginDtoRequest loginFirstRequest = new LoginDtoRequest(
                firstLogin,
                passwordHash);
        LoginDtoResponse responseFirst = userService.authorization(loginFirstRequest);
        String firstToken = responseFirst.token;

        final String secondLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerSecond = new RegistrationDtoRequest(
                secondLogin,
                passwordHash);
        userService.register(registerSecond);
        final LoginDtoRequest loginSecondRequest = new LoginDtoRequest(
                secondLogin,
                passwordHash);
        LoginDtoResponse responseSecond = userService.authorization(loginSecondRequest);
        String secondToken = responseSecond.token;

        final boolean withBot = false;
        final String blackColor = Color.BLACK.name();
        final int size = 9;
        final CreateGameDtoRequest createGameRequest = new CreateGameDtoRequest(
                withBot,
                blackColor,
                size,
                firstToken);


        final var gameDto = gameService.createGame(createGameRequest);
        assertAll(
                () -> assertEquals(ResponseInfoMessage.SUCCESS_CREATE_GAME.message, gameDto.message),
                () -> assertEquals(ResponseStatus.SUCCESS.text, gameDto.status),
                () -> assertNotNull(gameDto.gameId)
        );

        final String color = Color.WHITE.name();
        final JoinGameDtoRequest joinGameRequest = new JoinGameDtoRequest(
                gameDto.gameId,
                secondToken,
                color);

        final var dtoResponse = gameService.joinGame(joinGameRequest);
        final String expectedMessage = ResponseInfoMessage.SUCCESS_JOIN_GAME.message;
        final String expectedStatus = ResponseStatus.SUCCESS.text;
        assertAll(
                () -> assertEquals(expectedMessage, dtoResponse.message),
                () -> assertEquals(expectedStatus, dtoResponse.status)
        );


        final TurnDtoRequest turnDtoRequest = new TurnDtoRequest(
                blackColor,
                0,
                0,
                firstToken);

        final ActionDtoResponse actionDtoResponse = gameService.turn(turnDtoRequest);
        assertAll(
                () -> assertEquals(ResponseStatus.SUCCESS.text, actionDtoResponse.status),
                () -> assertEquals(ResponseInfoMessage.SUCCESS_TURN.message, actionDtoResponse.message),
                () -> assertNotNull(actionDtoResponse.gameField)
        );
    }

    @Test
    public void testPass() throws ServerException {
        final String firstLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerFirst = new RegistrationDtoRequest(
                firstLogin,
                passwordHash);
        userService.register(registerFirst);
        final LoginDtoRequest loginFirstRequest = new LoginDtoRequest(
                firstLogin,
                passwordHash);
        LoginDtoResponse responseFirst = userService.authorization(loginFirstRequest);
        String firstToken = responseFirst.token;

        final String secondLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerSecond = new RegistrationDtoRequest(
                secondLogin,
                passwordHash);
        userService.register(registerSecond);
        final LoginDtoRequest loginSecondRequest = new LoginDtoRequest(
                secondLogin,
                passwordHash);
        LoginDtoResponse responseSecond = userService.authorization(loginSecondRequest);
        String secondToken = responseSecond.token;

        final boolean withBot = false;
        final String blackColor = Color.BLACK.name();
        final int size = 9;
        final CreateGameDtoRequest createGameRequest = new CreateGameDtoRequest(
                withBot,
                blackColor,
                size,
                firstToken);


        final var gameDto = gameService.createGame(createGameRequest);
        assertAll(
                () -> assertEquals(ResponseInfoMessage.SUCCESS_CREATE_GAME.message, gameDto.message),
                () -> assertEquals(ResponseStatus.SUCCESS.text, gameDto.status),
                () -> assertNotNull(gameDto.gameId)
        );

        final String color = Color.WHITE.name();
        final JoinGameDtoRequest joinGameRequest = new JoinGameDtoRequest(
                gameDto.gameId,
                secondToken,
                color);

        final var dtoResponse = gameService.joinGame(joinGameRequest);
        final String expectedMessage = ResponseInfoMessage.SUCCESS_JOIN_GAME.message;
        final String expectedStatus = ResponseStatus.SUCCESS.text;
        assertAll(
                () -> assertEquals(expectedMessage, dtoResponse.message),
                () -> assertEquals(expectedStatus, dtoResponse.status)
        );


        final PassDtoRequest passDtoRequest = new PassDtoRequest(firstToken);

        final ActionDtoResponse actionDtoResponse = gameService.pass(passDtoRequest);
        assertAll(
                () -> assertEquals(ResponseStatus.SUCCESS.text, actionDtoResponse.status),
                () -> assertEquals(ResponseInfoMessage.SUCCESS_PASS.message, actionDtoResponse.message),
                () -> assertNotNull(actionDtoResponse.gameField)
        );
    }
}
