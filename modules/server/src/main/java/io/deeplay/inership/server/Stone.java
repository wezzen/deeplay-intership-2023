package io.deeplay.inership.server;

/**
 * Класс, представляющий камень для игры в Го. Камень имеет свой цвет, позицию на доске {@ling Cell}
 * и принадлежит определенной группе камней {@ling Group}.
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
