package io.deeplay.intership.exception.game;

public class GameException extends Exception {
    public final GameCode gameCode;

    public GameException(GameCode gameCode) {
        this.gameCode = gameCode;
    }
}
