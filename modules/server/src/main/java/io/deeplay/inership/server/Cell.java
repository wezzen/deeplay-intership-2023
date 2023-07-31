package io.deeplay.inership.server;

/**
 * Класс, представляющий ячейку с координатами.
 */
public class Cell {
  private int x;
  private int y;

  public Cell(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
