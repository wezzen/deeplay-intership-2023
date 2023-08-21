package io.deeplay.intership.game;

import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Player;
import io.deeplay.intership.model.Stone;

public class GameSession {
    private final String gameId;
    private final Game game;
    private Player creator;
    private Player blackPlayer;
    private Player whitePlayer;

    public GameSession(final String gameId) {
        this.gameId = gameId;
        this.game = new Game();
    }

    public String getGameId() {
        return gameId;
    }

    public synchronized void addCreator(final Player player) {
        creator = player;
    }

    public synchronized void addPlayer(final Player player) throws ServerException {
        if (!creator.color().equals(Color.EMPTY.name())) {
            checkEnemyColor(player);
        }
        if (player.login().equals(creator.login())) {
            throw new ServerException(ErrorCode.REPEATED_PLAYER);
        }

        chooseColor(player);
        if (blackPlayer == null) {
            blackPlayer = creator;
        } else {
            whitePlayer = creator;
        }
    }

    private void checkEnemyColor(final Player player) throws ServerException {
        Color color = Color.valueOf(player.color());
        if (color == Color.EMPTY || color == Color.valueOf(creator.color())) {
            throw new ServerException(ErrorCode.INVALID_COLOR);
        }
    }

    private void chooseColor(final Player player) {
        if (Color.BLACK.name().equals(player.color())) {
            blackPlayer = player;
        } else {
            whitePlayer = player;
        }
    }
}
