package io.deeplay.intership.service;

import io.deeplay.intership.game.Game;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Player;

public class GameSession {
    private final String gameId;
    private final Game game;
    private GameStatus gameStatus;
    private Player blackPlayer;
    private Player whitePlayer;

    public GameSession(String gameId) {
        this.gameId = gameId;
        this.game = new Game();
    }

    public void addPlayer(Player player) {
        if (isStarted()) {
            return;
        }
        Color color = player.color();
        if (color == Color.BLACK && blackPlayer == null) {
            blackPlayer = player;
        }
        if (color == Color.WHITE && whitePlayer == null) {
            whitePlayer = player;
        }
    }

    private boolean isStarted() {
        return gameStatus != GameStatus.WAIT_SECOND_PLAYER;
    }


    public String getGameId() {
        return gameId;
    }
}
