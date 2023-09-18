package io.deeplay.intership.decision.maker.terminal;

import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.mockito.Mockito.when;

public class DecisionMakerTerminalTest {
    @Test
    public void getLoginPassword_RegistrationTest() {
        final DecisionMaker decisionMaker = new DecisionMakerTerminal(new Scanner("1 asd dsa"));
        LoginPassword lp = null;
        try {
            lp = decisionMaker.getLoginPassword();
        } catch (ClientException e) {
            System.err.println(e.getMessage());
        }
        Assertions.assertEquals(lp.type(), RequestType.REGISTRATION);
        Assertions.assertEquals("asd", lp.login());
        Assertions.assertEquals("dsa", lp.password());
    }

    @Test
    public void getLoginPassword_LoginTest() throws ClientException {
        final DecisionMaker decisionMaker = new DecisionMakerTerminal(new Scanner("2 asd dsa"));
        LoginPassword lp = decisionMaker.getLoginPassword();

        Assertions.assertEquals(lp.type(), RequestType.LOGIN);
        Assertions.assertEquals("asd", lp.login());
        Assertions.assertEquals("dsa", lp.password());
    }


    @Test
    public void getLoginPassword_FailureTest() {
        final DecisionMaker decisionMaker = new DecisionMakerTerminal(new Scanner("999 asd dsa"));

        Assertions.assertThrows(ClientException.class, decisionMaker::getLoginPassword);
    }

    @Test
    public void getGameActionTest() {
        final DecisionMaker decisionMaker = new DecisionMakerTerminal(new Scanner("1 1 4 2 3"));
        final GameAction gameAction = new GameAction(RequestType.TURN, 0, 3);
        Assertions.assertEquals(gameAction, decisionMaker.getGameAction());
        Assertions.assertEquals(decisionMaker.getGameAction().type(), RequestType.PASS);
        Assertions.assertEquals(decisionMaker.getGameAction().type(), RequestType.SURRENDER);

        GameAction ga = new GameAction(RequestType.TURN, 0, 3);

        Assertions.assertEquals(3, ga.column());
        LoginPassword lp = new LoginPassword(RequestType.LOGIN, "sus", "ses");
        Assertions.assertEquals("sus", lp.login());

        GameConfig gi = new GameConfig(RequestType.CREATE_GAME, false, Color.BLACK, 9, "123");
        Assertions.assertEquals("123", gi.gameId());
    }

    @Test
    public void getColorTest() {
        DecisionMaker decisionMaker1 = new DecisionMakerTerminal(new Scanner("1 "));
        DecisionMaker decisionMaker2 = new DecisionMakerTerminal(new Scanner("2 "));
        DecisionMaker decisionMaker3 = new DecisionMakerTerminal(new Scanner("3 "));
        DecisionMaker decisionMaker4 = new DecisionMakerTerminal(new Scanner("999 "));

        Assertions.assertEquals(Color.WHITE, decisionMaker1.getColor());
        Assertions.assertEquals(Color.BLACK, decisionMaker2.getColor());
        Assertions.assertNotEquals(Color.EMPTY, decisionMaker3.getColor());
        Assertions.assertThrows(IllegalStateException.class, decisionMaker4::getColor);
    }

    @Test
    public void gameIdTest() {
        final DecisionMaker decisionMaker = Mockito.mock(DecisionMakerTerminal.class);
        final GameConfig gameConfig = new GameConfig(RequestType.JOIN_GAME, false, Color.BLACK, 9, "123");
        try {
            when(decisionMaker.getGameConfig()).thenReturn(gameConfig);
            Assertions.assertEquals(gameConfig, decisionMaker.getGameConfig());
        } catch (ClientException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void getGameIdTest() {
        final DecisionMaker decisionMaker = new DecisionMakerTerminal(new Scanner("2 2 1 9 1 2 12"));
        try {
            Assertions.assertEquals(decisionMaker.getGameConfig().type(), RequestType.CREATE_GAME);
            Assertions.assertEquals(decisionMaker.getGameConfig().type(), RequestType.JOIN_GAME);
        } catch (ClientException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testStartGame() {
        final DecisionMaker decisionMaker = new DecisionMakerTerminal(new Scanner(System.in));

        Assertions.assertDoesNotThrow(decisionMaker::startGame);
    }

    @Test
    public void testEndGame() {
        final DecisionMaker decisionMaker = new DecisionMakerTerminal(new Scanner(System.in));

        Assertions.assertDoesNotThrow(decisionMaker::endGame);
    }
}
