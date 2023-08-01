package io.deeplay.intership.service;

/**
 * Класс, представляющий камень для игры в Го. Камень имеет свой цвет и позицию на доске {@code x,y}
 * и принадлежит определенной группе камней {@link Group}.
 */

public class Stone {

  private Color color;
  private final int x;
  private final int y;
  private Group group;

  public Stone(Color color, int x, int y, Group group) {
    this.color = color;
    this.x = x;
    this.y = y;
    this.group = group;
  }

  public Stone(Color color, int x, int y) {
    this(color, x, y, null);
  }

  public Color getColor() {
    return color;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Group getGroup() {
    return group;
  }
}
