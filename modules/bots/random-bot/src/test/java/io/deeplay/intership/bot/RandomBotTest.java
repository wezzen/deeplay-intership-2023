package io.deeplay.intership.bot;

import io.deeplay.intership.bot.random.RandomBot;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RandomBotTest {
    private static final int MIN_POSITION_VALUE = 0;
    private static final int MAX_POSITION_VALUE = 19;

    @Test
    public void testConstructor() {
        final String name = UUID.randomUUID().toString();
        final Color blackColor = Color.BLACK;
        final Color whiteColor = Color.WHITE;
        final Color emptyColor = Color.EMPTY;

        assertDoesNotThrow(() -> new RandomBot(name, blackColor));
        assertDoesNotThrow(() -> new RandomBot(name, whiteColor));
        assertDoesNotThrow(() -> new RandomBot(name, emptyColor));
    }

    @Test
    public void testGetColor1() {
        final String name = UUID.randomUUID().toString();
        final Color color = Color.BLACK;
        final RandomBot randomBot = new RandomBot(name, color);

        assertEquals(color, randomBot.getColor());
    }

    @Test
    public void testGetColor2() {
        final String name = UUID.randomUUID().toString();
        final Color color = Color.WHITE;
        final RandomBot randomBot = new RandomBot(name, color);

        assertEquals(color, randomBot.getColor());
    }

    @Test
    public void testGetGameAction_TurnForBlack() {
        final String name = UUID.randomUUID().toString();
        final Color color = Color.BLACK;
        final RandomBot randomBot = new RandomBot(name, color);

        assertAll(
                () -> assertDoesNotThrow(randomBot::getGameAction),
                () -> assertNotNull(randomBot.getGameAction()),
                () -> assertTrue(randomBot.getGameAction().row() >= MIN_POSITION_VALUE),
                () -> assertTrue(randomBot.getGameAction().row() <= MAX_POSITION_VALUE),
                () -> assertTrue(randomBot.getGameAction().row() >= MIN_POSITION_VALUE),
                () -> assertTrue(randomBot.getGameAction().row() <= MAX_POSITION_VALUE),
                () -> assertEquals(RequestType.TURN, randomBot.getGameAction().type())
        );
    }

    @Test
    public void testGetGameAction_TurnForWhite() {
        final String name = UUID.randomUUID().toString();
        final Color color = Color.WHITE;
        final RandomBot randomBot = new RandomBot(name, color);

        assertAll(
                () -> assertDoesNotThrow(randomBot::getGameAction),
                () -> assertNotNull(randomBot.getGameAction()),
                () -> assertTrue(randomBot.getGameAction().row() >= MIN_POSITION_VALUE),
                () -> assertTrue(randomBot.getGameAction().row() <= MAX_POSITION_VALUE),
                () -> assertTrue(randomBot.getGameAction().row() >= MIN_POSITION_VALUE),
                () -> assertTrue(randomBot.getGameAction().row() <= MAX_POSITION_VALUE),
                () -> assertEquals(RequestType.TURN, randomBot.getGameAction().type())
        );
    }

    @Test
    public void testGetGameAction_PassForBlack() {
        final int boardSize = 9;
        final String name = UUID.randomUUID().toString();
        final Color color = Color.BLACK;
        final RandomBot randomBot = new RandomBot(name, color);

        final Stone[][] gameField = new Stone[boardSize][boardSize];
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                gameField[i][j] = new Stone(Color.BLACK, i, j);
            }
        }
        randomBot.setGameField(gameField);

        assertAll(
                () -> assertDoesNotThrow(randomBot::getGameAction),
                () -> assertNotNull(randomBot.getGameAction()),
                () -> assertEquals(RequestType.PASS, randomBot.getGameAction().type())
        );
    }

    @Test
    public void testGetGameAction_PassForWhite() {
        final int boardSize = 9;
        final String name = UUID.randomUUID().toString();
        final Color color = Color.WHITE;
        final RandomBot randomBot = new RandomBot(name, color);

        final Stone[][] gameField = new Stone[boardSize][boardSize];
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                gameField[i][j] = new Stone(Color.BLACK, i, j);
            }
        }
        randomBot.setGameField(gameField);

        assertAll(
                () -> assertDoesNotThrow(randomBot::getGameAction),
                () -> assertNotNull(randomBot.getGameAction()),
                () -> assertEquals(RequestType.PASS, randomBot.getGameAction().type())
        );
    }

    @Test
    public void testStartGame() {
        final String name = UUID.randomUUID().toString();
        final Color color = Color.WHITE;
        final RandomBot randomBot = new RandomBot(name, color);

        assertDoesNotThrow(randomBot::startGame);
    }

    @Test
    public void testEndGame() {
        final String name = UUID.randomUUID().toString();
        final Color color = Color.WHITE;
        final RandomBot randomBot = new RandomBot(name, color);

        assertDoesNotThrow(randomBot::endGame);
    }
}

