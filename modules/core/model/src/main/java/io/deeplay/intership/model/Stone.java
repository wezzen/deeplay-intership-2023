package io.deeplay.intership.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Класс, представляющий камень для игры в Го. Камень имеет свой цвет и позицию на доске {@code rowNumber, columnNumber}
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

    @JsonCreator
    public Stone(@JsonProperty("color") Color color, @JsonProperty("rowNumber") int rowNumber, @JsonProperty("columnNumber") int columnNumber) {
        this(color, rowNumber, columnNumber, new Group());
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

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setColor(Color color) {
        this.color = color;
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
                && color == stone.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, rowNumber, columnNumber);
    }
}
