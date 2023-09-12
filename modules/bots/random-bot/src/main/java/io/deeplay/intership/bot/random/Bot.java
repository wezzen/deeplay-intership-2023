package io.deeplay.intership.bot.random;

import io.deeplay.intership.decision.maker.GoPlayer;
import io.deeplay.intership.model.Color;

public abstract class Bot implements GoPlayer {
    protected final String name;
    protected final Color color;

    public Bot(String name, Color color) {
        this.name = name;
        this.color = color;
    }
}
