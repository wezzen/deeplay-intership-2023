
import io.deeplay.intership.dto.RequestType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class DecisionMakerTerminalTest {
    @Test
    public void getLoginPswdTest(){
        DecisionMakerTerminal dmt = new DecisionMakerTerminal();
        Assertions.assertEquals(null, dmt.getLoginPassword());
    }
    @Test
    public void getGameAction(){
        DecisionMakerTerminal dmt = new DecisionMakerTerminal();
        Assertions.assertEquals(null, dmt.getGameAction());
        GameAction ga = new GameAction(RequestType.TURN, 2, 5);
        Assertions.assertEquals(5, ga.row());
        LoginPassword lp = new LoginPassword(RequestType.LOGIN,"sus","ses");
        Assertions.assertEquals("sus", lp.login());
    }

}
