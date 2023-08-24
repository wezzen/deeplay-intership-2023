package io.deeplay.intership.decision.maker;

import io.deeplay.intership.model.Color;

<<<<<<< HEAD
import java.io.IOException;

public interface DecisionMaker {
    public LoginPassword getLoginPassword() throws IOException;

    public GameAction getGameAction() throws IOException;

    public GameId getGameId() throws IOException;

    public Color getColor() throws IOException;
=======
public interface DecisionMaker {
    public LoginPassword getLoginPassword();
    public GameAction getGameAction();
    public GameId getGameId();
    public Color getColor();
>>>>>>> 326b04d (эскиз gui)
}
