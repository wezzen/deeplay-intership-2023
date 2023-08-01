package io.deeplay.intership.service;

import java.util.Objects;

/**
 * Класс, представляющий камень для игры в Го. Камень имеет свой цвет и позицию на доске {@code x,y}
 * и принадлежит определенной группе камней {@link Group}.
 */

public class Stone {

  private Color color;
  private final int rowNumber;
  private final int columnNumber;
  private Group group;

  public Stone(Color color, int rowNumber, int columnNumber, Group group) {
    this.color = color;
    this.rowNumber = rowNumber;
    this.columnNumber = columnNumber;
    this.group = group;
  }

  public Stone(Color color, int rowNumber, int columnNumber) {
    this(color, rowNumber, columnNumber, null);
  }

  public Color getColor() {
    return color;
  }

  public int getRowNumber() {
    return rowNumber;
  }

  public int getColumnNumber() {
    return columnNumber;
  }

  public Group getGroup() {
    return group;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Stone stone = (Stone) o;
    return rowNumber == stone.rowNumber && columnNumber == stone.columnNumber
        && color == stone.color && Objects.equals(group,
        stone.group);
  }

  @Override
  public int hashCode() {
    return Objects.hash(color, rowNumber, columnNumber, group);
  }
}
