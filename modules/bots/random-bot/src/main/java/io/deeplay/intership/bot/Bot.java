package io.deeplay.intership.bot;

import io.deeplay.intership.decision.maker.GoPlayer;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;

public abstract class Bot implements GoPlayer {
    protected final String name;
    protected final Color color;
    protected Stone[][] gameField;
    public Bot(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    @Override
    public void setGameField(final Stone[][] gameField) {
        this.gameField = gameField;
    }
}
