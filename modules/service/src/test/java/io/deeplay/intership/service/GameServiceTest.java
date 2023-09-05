package io.deeplay.intership.service;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.*;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {
    private static final ConcurrentMap<String, GameSession> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();
    private static final GameService GAME_SERVICE = new GameService(CONCURRENT_HASH_MAP);
    private static final GameplayService GAMEPLAY_SERVICE = new GameplayService(CONCURRENT_HASH_MAP);
    private static final UserService USER_SERVICE = new UserService();
    private static final String LOGIN = UUID.randomUUID().toString();
    private static final String PASSWORD_HASH = UUID.randomUUID().toString();
    private static String token;

    @BeforeAll
    public static void initRegistration() {
        final RegistrationDtoRequest register = new RegistrationDtoRequest(
                LOGIN,
                PASSWORD_HASH);
        try {
            USER_SERVICE.register(register);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void initUserAuth() {
        final LoginDtoRequest loginRequest = new LoginDtoRequest(
                LOGIN,
                PASSWORD_HASH);
        LoginDtoResponse response = null;
        try {
            response = USER_SERVICE.authorization(loginRequest);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
        token = response.token;
    }

    @AfterEach
    public void initUserLogout() {
        final LogoutDtoRequest logoutRequest = new LogoutDtoRequest(token);
        try {
            USER_SERVICE.logout(logoutRequest);
        } catch (ServerException e) {

        }
    }

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new GameService(CONCURRENT_HASH_MAP));
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
        final ResponseStatus expectedStatus = ResponseStatus.SUCCESS;
        final CreateGameDtoResponse response = GAME_SERVICE.createGame(dto);
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

        assertThrows(ServerException.class, () -> GAME_SERVICE.createGame(dto));
    }

    @Test
    public void testSuccessJoinGame() throws ServerException {
        final String firstLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerFirst = new RegistrationDtoRequest(
                firstLogin,
                PASSWORD_HASH);
        USER_SERVICE.register(registerFirst);
        final LoginDtoRequest loginFirstRequest = new LoginDtoRequest(
                firstLogin,
                PASSWORD_HASH);
        LoginDtoResponse responseFirst = USER_SERVICE.authorization(loginFirstRequest);
        String firstToken = responseFirst.token;

        final String secondLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerSecond = new RegistrationDtoRequest(
                secondLogin,
                PASSWORD_HASH);
        USER_SERVICE.register(registerSecond);
        final LoginDtoRequest loginSecondRequest = new LoginDtoRequest(
                secondLogin,
                PASSWORD_HASH);
        LoginDtoResponse responseSecond = USER_SERVICE.authorization(loginSecondRequest);
        String secondToken = responseSecond.token;

        final boolean withBot = false;
        final String blackColor = Color.BLACK.name();
        final int size = 9;
        final CreateGameDtoRequest createGameRequest = new CreateGameDtoRequest(
                withBot,
                blackColor,
                size,
                firstToken);

        final var gameDto = GAME_SERVICE.createGame(createGameRequest);
        assertAll(
                () -> assertEquals(ResponseInfoMessage.SUCCESS_CREATE_GAME.message, gameDto.message),
                () -> assertEquals(ResponseStatus.SUCCESS, gameDto.status),
                () -> assertNotNull(gameDto.gameId)
        );

        final String color = Color.WHITE.name();
        final JoinGameDtoRequest joinGameRequest = new JoinGameDtoRequest(
                gameDto.gameId,
                secondToken,
                color);

        final var dtoResponse = GAME_SERVICE.joinGame(joinGameRequest);
        final String expectedMessage = ResponseInfoMessage.SUCCESS_JOIN_GAME.message;
        final ResponseStatus expectedStatus = ResponseStatus.SUCCESS;
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
                PASSWORD_HASH);
        USER_SERVICE.register(registerFirst);
        final LoginDtoRequest loginFirstRequest = new LoginDtoRequest(
                firstLogin,
                PASSWORD_HASH);
        LoginDtoResponse responseFirst = USER_SERVICE.authorization(loginFirstRequest);
        String firstToken = responseFirst.token;

        final String secondLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerSecond = new RegistrationDtoRequest(
                secondLogin,
                PASSWORD_HASH);
        USER_SERVICE.register(registerSecond);
        final LoginDtoRequest loginSecondRequest = new LoginDtoRequest(
                secondLogin,
                PASSWORD_HASH);
        LoginDtoResponse responseSecond = USER_SERVICE.authorization(loginSecondRequest);
        String secondToken = responseSecond.token;

        final boolean withBot = false;
        final String blackColor = Color.EMPTY.name();
        final int size = 9;
        final CreateGameDtoRequest createGameRequest = new CreateGameDtoRequest(
                withBot,
                blackColor,
                size,
                firstToken);

        final var gameDto = GAME_SERVICE.createGame(createGameRequest);
        assertAll(
                () -> assertEquals(ResponseInfoMessage.SUCCESS_CREATE_GAME.message, gameDto.message),
                () -> assertEquals(ResponseStatus.SUCCESS, gameDto.status),
                () -> assertNotNull(gameDto.gameId)
        );

        final String color = Color.WHITE.name();
        final JoinGameDtoRequest joinGameRequest = new JoinGameDtoRequest(
                gameDto.gameId,
                secondToken,
                color);

        final var dtoResponse = GAME_SERVICE.joinGame(joinGameRequest);
        final String expectedMessage = ResponseInfoMessage.SUCCESS_JOIN_GAME.message;
        final ResponseStatus expectedStatus = ResponseStatus.SUCCESS;
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
        GAME_SERVICE.createGame(createGameRequest);

        final String color = Color.BLACK.name();
        final String gameId = UUID.randomUUID().toString();
        final JoinGameDtoRequest joinGameRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> GAME_SERVICE.joinGame(joinGameRequest));
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

        assertThrows(ServerException.class, () -> GAME_SERVICE.joinGame(dto));
    }

    @Test
    public void testSurrenderGame() {
        final String token = UUID.randomUUID().toString();
        final SurrenderDtoRequest dto = new SurrenderDtoRequest(token);

        assertDoesNotThrow(() -> GAMEPLAY_SERVICE.surrenderGame(dto));
    }

    @Test
    public void testFinishGame() {
        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);
        assertDoesNotThrow(() -> GAMEPLAY_SERVICE.finishGame(gameSession));
    }

    @Test
    public void testSuccessTurn() throws ServerException {
        final String firstLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerFirst = new RegistrationDtoRequest(
                firstLogin,
                PASSWORD_HASH);
        USER_SERVICE.register(registerFirst);
        final LoginDtoRequest loginFirstRequest = new LoginDtoRequest(
                firstLogin,
                PASSWORD_HASH);
        LoginDtoResponse responseFirst = USER_SERVICE.authorization(loginFirstRequest);
        String firstToken = responseFirst.token;

        final String secondLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerSecond = new RegistrationDtoRequest(
                secondLogin,
                PASSWORD_HASH);
        USER_SERVICE.register(registerSecond);
        final LoginDtoRequest loginSecondRequest = new LoginDtoRequest(
                secondLogin,
                PASSWORD_HASH);
        LoginDtoResponse responseSecond = USER_SERVICE.authorization(loginSecondRequest);
        String secondToken = responseSecond.token;

        final boolean withBot = false;
        final String blackColor = Color.BLACK.name();
        final int size = 9;
        final CreateGameDtoRequest createGameRequest = new CreateGameDtoRequest(
                withBot,
                blackColor,
                size,
                firstToken);


        final var gameDto = GAME_SERVICE.createGame(createGameRequest);
        assertAll(
                () -> assertEquals(ResponseInfoMessage.SUCCESS_CREATE_GAME.message, gameDto.message),
                () -> assertEquals(ResponseStatus.SUCCESS, gameDto.status),
                () -> assertNotNull(gameDto.gameId)
        );

        final String color = Color.WHITE.name();
        final JoinGameDtoRequest joinGameRequest = new JoinGameDtoRequest(
                gameDto.gameId,
                secondToken,
                color);

        final var dtoResponse = GAME_SERVICE.joinGame(joinGameRequest);
        final String expectedMessage = ResponseInfoMessage.SUCCESS_JOIN_GAME.message;
        final ResponseStatus expectedStatus = ResponseStatus.SUCCESS;
        assertAll(
                () -> assertEquals(expectedMessage, dtoResponse.message),
                () -> assertEquals(expectedStatus, dtoResponse.status)
        );


        final TurnDtoRequest turnDtoRequest = new TurnDtoRequest(
                blackColor,
                0,
                0,
                firstToken);

        final ActionDtoResponse actionDtoResponse = GAMEPLAY_SERVICE.turn(turnDtoRequest);
        assertAll(
                () -> assertEquals(ResponseStatus.SUCCESS, actionDtoResponse.status),
                () -> assertEquals(ResponseInfoMessage.SUCCESS_TURN.message, actionDtoResponse.message),
                () -> assertNotNull(actionDtoResponse.gameField)
        );
    }

    @Test
    public void testPass() throws ServerException {
        final String firstLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerFirst = new RegistrationDtoRequest(
                firstLogin,
                PASSWORD_HASH);
        USER_SERVICE.register(registerFirst);
        final LoginDtoRequest loginFirstRequest = new LoginDtoRequest(
                firstLogin,
                PASSWORD_HASH);
        LoginDtoResponse responseFirst = USER_SERVICE.authorization(loginFirstRequest);
        String firstToken = responseFirst.token;

        final String secondLogin = UUID.randomUUID().toString();
        final RegistrationDtoRequest registerSecond = new RegistrationDtoRequest(
                secondLogin,
                PASSWORD_HASH);
        USER_SERVICE.register(registerSecond);
        final LoginDtoRequest loginSecondRequest = new LoginDtoRequest(
                secondLogin,
                PASSWORD_HASH);
        LoginDtoResponse responseSecond = USER_SERVICE.authorization(loginSecondRequest);
        String secondToken = responseSecond.token;

        final boolean withBot = false;
        final String blackColor = Color.BLACK.name();
        final int size = 9;
        final CreateGameDtoRequest createGameRequest = new CreateGameDtoRequest(
                withBot,
                blackColor,
                size,
                firstToken);


        final var gameDto = GAME_SERVICE.createGame(createGameRequest);
        assertAll(
                () -> assertEquals(ResponseInfoMessage.SUCCESS_CREATE_GAME.message, gameDto.message),
                () -> assertEquals(ResponseStatus.SUCCESS, gameDto.status),
                () -> assertNotNull(gameDto.gameId)
        );

        final String color = Color.WHITE.name();
        final JoinGameDtoRequest joinGameRequest = new JoinGameDtoRequest(
                gameDto.gameId,
                secondToken,
                color);

        final var dtoResponse = GAME_SERVICE.joinGame(joinGameRequest);
        final String expectedMessage = ResponseInfoMessage.SUCCESS_JOIN_GAME.message;
        final ResponseStatus expectedStatus = ResponseStatus.SUCCESS;
        assertAll(
                () -> assertEquals(expectedMessage, dtoResponse.message),
                () -> assertEquals(expectedStatus, dtoResponse.status)
        );


        final PassDtoRequest passDtoRequest = new PassDtoRequest(firstToken);

        final ActionDtoResponse actionDtoResponse = (ActionDtoResponse) GAMEPLAY_SERVICE.pass(passDtoRequest);
        assertAll(
                () -> assertEquals(ResponseStatus.SUCCESS, actionDtoResponse.status),
                () -> assertEquals(ResponseInfoMessage.SUCCESS_PASS.message, actionDtoResponse.message),
                () -> assertNotNull(actionDtoResponse.gameField)
        );
    }
}
