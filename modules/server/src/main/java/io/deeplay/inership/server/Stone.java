
package io.deeplay.inership.server;

public class Stone {
    private Color color;
    private Cell cell;
    private Group sequence;

    public Color getColor() {
        return color;
    }

    public Stone(Color color, Cell cell, Group group) {
        this.color = color;
        this.cell = cell;
        this.sequence = group;
    }
    public void setColor(Color color1) {
        this.color = color1;
    }
}