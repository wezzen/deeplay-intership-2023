package io.deeplay.intership.decision.maker.terminal;

import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GameId;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Scanner;

import static org.mockito.Mockito.when;
public class DecisionMakerTerminalTest {
    @Test
    public void getLoginPswdTest(){
        DecisionMakerTerminal dmt = new DecisionMakerTerminal(new Scanner("1 asd dsa"));
        LoginPassword lp = null;
        try {
            lp = dmt.getLoginPassword();
        } catch (IOException e) {
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
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        GameAction ga = new GameAction(RequestType.TURN, 2, 5);

        Assertions.assertEquals(5, ga.row());
        LoginPassword lp = new LoginPassword(RequestType.LOGIN,"sus","ses");
        Assertions.assertEquals("sus", lp.login());

        GameId gi = new GameId(RequestType.CREATE_GAME, false, Color.BLACK, 9,123);
        Assertions.assertEquals(123, gi.gameId());
    }
    @Test
    public void getColorTest(){
        DecisionMakerTerminal dmt = new DecisionMakerTerminal(new Scanner("3 "));
        try {
            Assertions.assertNotEquals(Color.EMPTY, dmt.getColor());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @Test
    public void gameIdTest(){
        DecisionMakerTerminal dmt = Mockito.mock(DecisionMakerTerminal.class);
        final GameId gameId = new GameId(RequestType.JOIN_GAME, false, Color.BLACK, 9,123);
        try {
            when(dmt.getGameId()).thenReturn(gameId);
            Assertions.assertEquals(gameId, dmt.getGameId());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void getGameIdTest() {
        DecisionMakerTerminal dmt = new DecisionMakerTerminal(new Scanner("2 2 1 9 1 2 12"));
        try {
            Assertions.assertEquals(dmt.getGameId().type(), RequestType.CREATE_GAME);
            Assertions.assertEquals(dmt.getGameId().type(), RequestType.JOIN_GAME);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
