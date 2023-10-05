package io.deeplay.intership.model;

public interface GoPlayer {
    String getName();

    Color getColor();

    Answer getGameAction();

    void startGame();

    void endGame();
}
