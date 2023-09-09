package io.deeplay.intership.decision.maker;

import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;

public interface DecisionMaker {
    public LoginPassword getLoginPassword() throws ClientException;

    public GameAction getGameAction() throws ClientException;

    public GameConfig getGameConfig() throws ClientException;

    public Color getColor() throws ClientException;
}
