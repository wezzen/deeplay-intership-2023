package io.deeplay.intership.server;

import io.deeplay.intership.game.Game;
import io.deeplay.intership.game.GameSession;

import java.util.Arrays;
import java.util.Objects;

public class ServerGame implements Runnable {
    private static final int PLAYERS_COUNT = 2;
    private final GoPlayer[] players = new GoPlayer[PLAYERS_COUNT];
    private final String gameId;
    private final GameSession gameSession;
    private final Game game;
    private int totalPlayers;

    public ServerGame(String gameId) {
        this.gameId = gameId;
        this.gameSession = new GameSession(gameId);
        this.game = new Game();
        this.totalPlayers = 0;
    }

    public void joinPlayer(final String name, final GoPlayer player) {
        players[totalPlayers++] = player;
    }

    @Override
    public void run() {
        for (GoPlayer player : players) {
            player.startGame();
        }
        while (!game.gameIsOver()) {
            final int nextPlayerToAction = gameSession.getNextPlayer();
            final Answer answer = players[nextPlayerToAction].getGameAction();
        }
    }

    public boolean isCompletable() {
        return Arrays.stream(players).noneMatch(Objects::isNull);
    }
}
