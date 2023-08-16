package io.deeplay.intership.service;

import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class GameServiceTest {
    private final GameService gameService = new GameService();

    @Test
    public void testConstructor() {
        assertDoesNotThrow(GameService::new);
    }

    @Test
    public void testCreateGame() {
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dto = new CreateGameDtoRequest(
                RequestType.CREATE_GAME,
                withBot,
                color,
                size,
                token);

        assertDoesNotThrow(() -> gameService.createGame(dto));
    }

    @Test
    public void testJoinGame() {
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final JoinGameDtoRequest dto = new JoinGameDtoRequest(
                RequestType.CREATE_GAME,
                gameId,
                token);

        assertDoesNotThrow(() -> gameService.joinGame(dto));
    }

    @Test
    public void testSurrenderGame() {
        final String token = UUID.randomUUID().toString();
        final SurrenderDtoRequest dto = new SurrenderDtoRequest(
                RequestType.SURRENDER,
                token);

        assertDoesNotThrow(() -> gameService.surrenderGame(dto));
    }

    @Test
    public void testTurn() {
        final String color = Color.WHITE.name();
        final int row = 0;
        final int column = 0;
        final String token = UUID.randomUUID().toString();
        final TurnDtoRequest dto = new TurnDtoRequest(
                RequestType.TURN,
                color,
                row,
                column,
                token);

        assertDoesNotThrow(() -> gameService.turn(dto));
    }

    @Test
    public void testPass() {
        final String token = UUID.randomUUID().toString();
        final PassDtoRequest dto = new PassDtoRequest(
                RequestType.CREATE_GAME,
                token);

        assertDoesNotThrow(() -> gameService.pass(dto));
    }
}
