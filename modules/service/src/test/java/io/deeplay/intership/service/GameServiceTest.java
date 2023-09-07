package io.deeplay.intership.service;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.response.CreateGameDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Player;
import io.deeplay.intership.model.User;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameServiceTest {
    private final ConcurrentMap<String, GameSession> idToGameSession = mock(ConcurrentHashMap.class);
    private final ConcurrentMap<String, User> tokenToUser = mock(ConcurrentHashMap.class);
    private final ConcurrentMap<String, String> playerToGame = mock(ConcurrentHashMap.class);
    private final DataCollectionsAggregator collectionsAggregator = new DataCollectionsAggregator(idToGameSession, tokenToUser, playerToGame);
    private final GameService gameService = new GameService(collectionsAggregator);
    private final GameplayService gameplayService = new GameplayService(collectionsAggregator);

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new GameService(collectionsAggregator));
    }

    @Test
    public void testSuccessCreateGame() throws ServerException {
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 9;

        final CreateGameDtoRequest dto = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);
        final User user = new User(login, password);

        when(collectionsAggregator.tokenToUser().get(token)).thenReturn(user);

        final String expectedMessage = ResponseInfoMessage.SUCCESS_CREATE_GAME.message;
        final ResponseStatus expectedStatus = ResponseStatus.SUCCESS;
        final CreateGameDtoResponse response = gameService.createGame(dto);
        assertAll(
                () -> assertEquals(expectedMessage, response.message),
                () -> assertEquals(expectedStatus, response.status),
                () -> assertNotNull(response.gameId)
        );
    }

    @Test
    public void testFailureCreateGame_WithBadToken() throws ServerException {
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
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();

        final String color = Color.WHITE.name();
        final JoinGameDtoRequest joinGameRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        final User user = new User(login, password);
        final GameSession gameSession = new GameSession(gameId);
        gameSession.addCreator(new Player("dads", Color.BLACK.name()));

        when(collectionsAggregator.tokenToUser().get(token)).thenReturn(user);
        when(collectionsAggregator.playerToGame().get(token)).thenReturn(gameId);
        when(collectionsAggregator.idToGameSession().get(gameId)).thenReturn(gameSession);

        final var dtoResponse = gameService.joinGame(joinGameRequest);
        final String expectedMessage = ResponseInfoMessage.SUCCESS_JOIN_GAME.message;
        final ResponseStatus expectedStatus = ResponseStatus.SUCCESS;
        assertAll(
                () -> assertEquals(expectedMessage, dtoResponse.message),
                () -> assertEquals(expectedStatus, dtoResponse.status)
        );
    }

    @Test
    public void testFailureJoinGame_withBadToken() throws ServerException {
        final String token = UUID.randomUUID().toString();
        final String color = Color.BLACK.name();
        final String gameId = UUID.randomUUID().toString();
        final JoinGameDtoRequest joinGameRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> gameService.joinGame(joinGameRequest));
    }

    @Test
    public void testSurrenderGame() {
        final String token = UUID.randomUUID().toString();
        final SurrenderDtoRequest dto = new SurrenderDtoRequest(token);

        assertDoesNotThrow(() -> gameplayService.surrenderGame(dto));
    }

    @Test
    public void testFinishGame() {
        final String gameId = UUID.randomUUID().toString();
        final GameSession gameSession = new GameSession(gameId);

        assertDoesNotThrow(() -> gameplayService.finishGame(gameSession));
    }

    @Test
    public void testSuccessTurn() throws ServerException {
        final String blackColor = Color.BLACK.name();
        final String token = UUID.randomUUID().toString();
        final TurnDtoRequest turnDtoRequest = new TurnDtoRequest(
                blackColor,
                0,
                0,
                token);

        assertThrows(ServerException.class, () -> gameplayService.turn(turnDtoRequest));
    }

    @Test
    public void testPass() throws ServerException {
        final String token = UUID.randomUUID().toString();
        final PassDtoRequest passDtoRequest = new PassDtoRequest(token);

        assertThrows(ServerException.class, () -> gameplayService.pass(passDtoRequest));
    }
}
