package io.deeplay.intership.server;

import io.deeplay.intership.model.Color;

public interface GoPlayer {
    String getName();
    Color getColor();

    Answer getGameAction();

    void startGame();

    void endGame();
}
