package io.deeplay.intership.decision.maker;

import io.deeplay.intership.model.Color;

public interface DecisionMaker {
    public LoginPassword getLoginPassword();
    public GameAction getGameAction();
    public GameId getGameId();
    public Color getColor();
}
