package io.deeplay.intership.decision.maker;

import io.deeplay.intership.model.Color;

public interface GoPlayer {
    Color getColor();

    GameAction getGameAction();

    void startGame();

    void endGame();
}
