package io.deeplay.intership.server;

import io.deeplay.intership.model.Color;

public class ServerGoPlayer implements GoPlayer{
    private final String name;
    private final Color color;

    public ServerGoPlayer(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Answer getGameAction() {

        return null;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void endGame() {

    }
}
