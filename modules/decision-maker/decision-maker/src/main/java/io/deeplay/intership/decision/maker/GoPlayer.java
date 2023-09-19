package io.deeplay.intership.decision.maker;

import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;

public interface GoPlayer {
    Color getColor();

    GameAction getGameAction();

    void startGame();

    void endGame();

    void setGameField(Stone[][] gameField);
}
