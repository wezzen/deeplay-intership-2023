package io.deeplay.intership.decision.maker;

import io.deeplay.intership.exception.ClientException;

public interface DecisionMaker extends GoPlayer {
    LoginPassword getLoginPassword() throws ClientException;


    GameConfig getGameConfig() throws ClientException;

}
