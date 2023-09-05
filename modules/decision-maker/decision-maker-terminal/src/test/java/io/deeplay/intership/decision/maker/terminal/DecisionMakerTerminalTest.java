package io.deeplay.intership.decision.maker.terminal;

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
    public void getLoginPswdTest(){
        DecisionMakerTerminal dmt = new DecisionMakerTerminal(new Scanner("1 asd dsa"));
        LoginPassword lp = null;
        try {
            lp = dmt.getLoginPassword();
        } catch (ClientException e) {
            System.err.println(e.getMessage());
        }
        Assertions.assertEquals(lp.type(), RequestType.REGISTRATION);
        Assertions.assertEquals("asd", lp.login());
        Assertions.assertEquals("dsa", lp.password());
    }
    @Test
    public void getGameActionTest(){
        DecisionMakerTerminal dmt = new DecisionMakerTerminal(new Scanner("1 1 4 2 3"));
        final GameAction gameAction = new GameAction(RequestType.TURN, 1, 4);
        try {
            Assertions.assertEquals(gameAction, dmt.getGameAction());
            Assertions.assertEquals(dmt.getGameAction().type(), RequestType.PASS);
            Assertions.assertEquals(dmt.getGameAction().type(), RequestType.SURRENDER);
        } catch (ClientException e) {
            System.err.println(e.getMessage());
        }
        GameAction ga = new GameAction(RequestType.TURN, 2, 5);

        Assertions.assertEquals(5, ga.column());
        LoginPassword lp = new LoginPassword(RequestType.LOGIN,"sus","ses");
        Assertions.assertEquals("sus", lp.login());

        GameConfig gi = new GameConfig(RequestType.CREATE_GAME, false, Color.BLACK, 9, "123");
        Assertions.assertEquals("123", gi.gameId());
    }
    @Test
    public void getColorTest(){
        DecisionMakerTerminal dmt = new DecisionMakerTerminal(new Scanner("3 "));
        try {
            Assertions.assertNotEquals(Color.EMPTY, dmt.getColor());
        } catch (ClientException e) {
            System.err.println(e.getMessage());
        }
    }
    @Test
    public void gameIdTest(){
        DecisionMakerTerminal dmt = Mockito.mock(DecisionMakerTerminal.class);
        final GameConfig gameConfig = new GameConfig(RequestType.JOIN_GAME, false, Color.BLACK, 9, "123");
        try {
            when(dmt.getGameConfig()).thenReturn(gameConfig);
            Assertions.assertEquals(gameConfig, dmt.getGameConfig());
        } catch (ClientException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void getGameIdTest() {
        DecisionMakerTerminal dmt = new DecisionMakerTerminal(new Scanner("2 2 1 9 1 2 12"));
        try {
            Assertions.assertEquals(dmt.getGameConfig().type(), RequestType.CREATE_GAME);
            Assertions.assertEquals(dmt.getGameConfig().type(), RequestType.JOIN_GAME);
        } catch (ClientException e) {
            System.err.println(e.getMessage());
        }
    }
}