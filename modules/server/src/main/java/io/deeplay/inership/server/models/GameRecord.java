package io.deeplay.inership.server.models;

import java.util.ArrayList;
import java.util.List;

public class GameRecord {

  private final List<Board> gameHistory;

  public GameRecord() {
    gameHistory = new ArrayList<>();
  }

  public void write(final Board board) {
    //TODO: когда будет готов класс Board, реализовать копирование объекта
    gameHistory.add(new Board());
  }

  public List<Board> getGameHistory() {
    return gameHistory;
  }
}
