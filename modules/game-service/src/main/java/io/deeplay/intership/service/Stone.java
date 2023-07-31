package io.deeplay.intership.service;

/**
 * Класс, представляющий камень для игры в Го. Камень имеет свой цвет, позицию на доске {@link Cell}
 * и принадлежит определенной группе камней {@link Group}.
 */

public class Stone {

  private Color color;
  private Cell cell;
  private Group group;

  public Stone(Color color, Cell cell, Group group) {
    this.color = color;
    this.cell = cell;
    this.group = group;
  }

  public Stone(Color color, Cell cell) {
    this(color, cell, null);
  }

  public Color getColor() {
    return color;
  }

  public Cell getCell() {
    return cell;
  }

  public Group getGroup() {
    return group;
  }
}
