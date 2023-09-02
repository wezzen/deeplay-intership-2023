package io.deeplay.intership.bot;

import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomBotTest {
    private static final int MIN_POSITION_VALUE = 0;
    private static final int MAX_POSITION_VALUE = 19;

    @Test
    public void testConstructor() {
        final Color blackColor = Color.BLACK;
        final Color whiteColor = Color.WHITE;
        final Color emptyColor = Color.EMPTY;

        assertDoesNotThrow(() -> new RandomBot(blackColor));
        assertDoesNotThrow(() -> new RandomBot(whiteColor));
        assertDoesNotThrow(() -> new RandomBot(emptyColor));
    }

    @Test
    public void testGetColor1() throws ClientException {
        final Color color = Color.BLACK;
        final RandomBot randomBot = new RandomBot(color);

        assertEquals(color, randomBot.getColor());
    }

    @Test
    public void testGetColor2() throws ClientException {
        final Color color = Color.WHITE;
        final RandomBot randomBot = new RandomBot(color);

        assertEquals(color, randomBot.getColor());
    }

    @Test
    public void testGetGameAction_TurnForBlack() {
        final Color color = Color.BLACK;
        final RandomBot randomBot = new RandomBot(color);

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
        final Color color = Color.WHITE;
        final RandomBot randomBot = new RandomBot(color);

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
        final Color color = Color.BLACK;
        final RandomBot randomBot = new RandomBot(color);

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
        final Color color = Color.WHITE;
        final RandomBot randomBot = new RandomBot(color);

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
    public void testGetLoginPassword_ForRegistration() throws ClientException {
        final Color color = Color.BLACK;
        final RandomBot randomBot = new RandomBot(color);

        final LoginPassword result = randomBot.getLoginPassword();
        assertAll(
                () -> assertNotNull(result.type()),
                () -> assertEquals(RequestType.REGISTRATION, result.type()),
                () -> assertNotNull(result.login()),
                () -> assertNotNull(result.password())
        );
    }

    @Test
    public void testGetLoginPassword_ForLogin() throws ClientException {
        final Color color = Color.BLACK;
        final RandomBot randomBot = new RandomBot(color);

        randomBot.getLoginPassword();
        final LoginPassword result = randomBot.getLoginPassword();
        assertAll(
                () -> assertNotNull(result.type()),
                () -> assertEquals(RequestType.LOGIN, result.type()),
                () -> assertNotNull(result.login()),
                () -> assertNotNull(result.password())
        );
    }

    @Test
    public void testGetLoginPassword_FinalFields() throws ClientException {
        final Color color = Color.BLACK;
        final RandomBot randomBot = new RandomBot(color);

        final LoginPassword firstResult = randomBot.getLoginPassword();
        final LoginPassword secondResult = randomBot.getLoginPassword();
        assertAll(
                () -> assertEquals(RequestType.REGISTRATION, firstResult.type()),
                () -> assertEquals(RequestType.LOGIN, secondResult.type()),
                () -> assertNotEquals(firstResult.type(), secondResult.type()),

                () -> assertNotNull(firstResult.login()),
                () -> assertNotNull(firstResult.password()),
                () -> assertNotNull(secondResult.login()),
                () -> assertNotNull(secondResult.password()),

                () -> assertEquals(firstResult.login(), secondResult.login()),
                () -> assertEquals(firstResult.password(), secondResult.password())
        );
    }

    @Test
    public void testGameConfig_ForBlack() throws ClientException {
        final int size = 9;
        final Color color = Color.BLACK;
        final RandomBot randomBot = new RandomBot(color);

        final GameConfig result = randomBot.getGameConfig();

        assertAll(
                () -> assertNotNull(result.type()),
                () -> assertEquals(RequestType.CREATE_GAME, result.type()),
                () -> assertFalse(result.withBot()),
                () -> assertEquals(color, result.color()),
                () -> assertEquals(size, result.size()),
                () -> assertNotNull(result.gameId())
        );
    }

    @Test
    public void testGameConfig_ForWhite() throws ClientException {
        final int size = 9;
        final Color color = Color.WHITE;
        final RandomBot randomBot = new RandomBot(color);

        final GameConfig result = randomBot.getGameConfig();

        assertAll(
                () -> assertNotNull(result.type()),
                () -> assertEquals(RequestType.JOIN_GAME, result.type()),
                () -> assertFalse(result.withBot()),
                () -> assertEquals(color, result.color()),
                () -> assertEquals(size, result.size()),
                () -> assertNotNull(result.gameId())
        );
    }
}

