
import io.deeplay.intership.decision.maker.DecisionMakerTerminal;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GameId;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
public class DecisionMakerTerminalTest {
    @Test
    public void getLoginPswdTest(){
        DecisionMakerTerminal dmt = mock(DecisionMakerTerminal.class);
        final LoginPassword loginPassword = new LoginPassword(RequestType.LOGIN, "sus", "sos");
        when(dmt.getLoginPassword()).thenReturn(loginPassword);
        Assertions.assertEquals(loginPassword, dmt.getLoginPassword());
    }
    @Test
    public void getGameActionTest(){
        DecisionMakerTerminal dmt = mock(DecisionMakerTerminal.class);
        final GameAction gameAction = new GameAction(RequestType.TURN, 1, 4);
        when(dmt.getGameAction()).thenReturn(gameAction);
        Assertions.assertEquals(gameAction, dmt.getGameAction());
        GameAction ga = new GameAction(RequestType.TURN, 2, 5);
        Assertions.assertEquals(5, ga.row());
        LoginPassword lp = new LoginPassword(RequestType.LOGIN,"sus","ses");
        Assertions.assertEquals("sus", lp.login());
        GameId gi = new GameId(RequestType.CREATE_GAME, false, Color.BLACK, 9,123);
        Assertions.assertEquals(123, gi.gameId());
    }
    @Test
    public void getColorTest(){
        DecisionMakerTerminal dmt = mock(DecisionMakerTerminal.class);
        when(dmt.getColor()).thenReturn(Color.WHITE);
        Assertions.assertEquals(Color.WHITE, dmt.getColor());
    }
    @Test
    public void gameIdTest(){
        DecisionMakerTerminal dmt = mock(DecisionMakerTerminal.class);
        final GameId gameId = new GameId(RequestType.JOIN_GAME, false, Color.BLACK, 9,123);
        when(dmt.getGameId()).thenReturn(gameId);
        Assertions.assertEquals(gameId, dmt.getGameId());
    }
}
