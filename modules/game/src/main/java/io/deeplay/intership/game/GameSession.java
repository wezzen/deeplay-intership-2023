package io.deeplay.intership.game;

import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Player;
import io.deeplay.intership.model.Stone;

public class GameSession {
    private final String gameId;
    private final Game game;
    private boolean isStarted;
    private Player creator;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentTurn;

    public GameSession(final String gameId) {
        this.gameId = gameId;
        this.game = new Game();
        this.isStarted = false;
    }

    public String getGameId() {
        return gameId;
    }

    public void addCreator(final Player player) {
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
        currentTurn = blackPlayer;
        isStarted = true;
    }

    public synchronized Stone[][] turn(final Player player, final Stone stone) throws ServerException {
        isNotStarted();
        checkTurnOrder(player);
        return game.makeMove(stone);
    }

    public synchronized Stone[][] pass(final Player player) throws ServerException {
        isNotStarted();
        checkTurnOrder(player);
        return game.skipTurn(Color.valueOf(player.color()));
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

    private synchronized void isNotStarted() throws ServerException {
        if (!isStarted) {
            throw new ServerException(ErrorCode.GAME_NOT_STARTED);
        }
    }

    private synchronized void checkTurnOrder(final Player player) throws ServerException {
        if (!currentTurn.login().equals(player.login())) {
            throw new ServerException(ErrorCode.INVALID_TURN_ORDER);
        }
    }
}
