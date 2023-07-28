package io.deeplay.inership.server;

import java.util.ArrayList;
import java.util.List;

public class GameRecord {

    private final List<Board> gameHistory;

    public GameRecord() {
        gameHistory = new ArrayList<>();
    }

    public void write(final Board board) {
        gameHistory.add(board);
    }

    public List<Board> getGameHistory() {
        return gameHistory;
    }
}