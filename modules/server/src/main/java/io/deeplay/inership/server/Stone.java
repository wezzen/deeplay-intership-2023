
package io.deeplay.inership.server;

public class Stone {
    private Color color;
    private int xPosition, yPosition;
    private Group sequence;

    public Color getColor() {
        return color;
    }

    public Stone(Color color, int x, int y, Group group) {
        this.color = color;
        this.xPosition = x;
        this.yPosition = y;
        this.sequence = group;
    }
    public void setColor(Color color1) {
        this.color = color1;
    }
}