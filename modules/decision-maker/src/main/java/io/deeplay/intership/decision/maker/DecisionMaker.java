package io.deeplay.intership.decision.maker;

import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;

<<<<<<< HEAD
import java.io.IOException;

public interface DecisionMaker {
    public LoginPassword getLoginPassword() throws ClientException;

    public GameAction getGameAction() throws ClientException;

    public GameId getGameId() throws ClientException;

<<<<<<< HEAD
    public Color getColor() throws ClientException;
=======
    public Color getColor() throws IOException;
=======
public interface DecisionMaker {
    public LoginPassword getLoginPassword();
    public GameAction getGameAction();
    public GameId getGameId();
    public Color getColor();
>>>>>>> 326b04d (эскиз gui)
>>>>>>> 666915c (эскиз gui)
}
