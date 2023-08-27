package io.deeplay.intership.decision.maker;

import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;

import java.io.IOException;

public interface DecisionMaker {
    public LoginPassword getLoginPassword() throws ClientException;

    public GameAction getGameAction() throws ClientException;

    public GameId getGameId() throws ClientException;

    public Color getColor() throws ClientException;
}
