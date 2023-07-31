public class Stone {
    private Cell cell;
    private Group sequence;
    private Color color;

    Stone(int x, int y){
        cell = new Cell(x, y);
        this.color = Color.EMPTY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Group getSequence() {
        return sequence;
    }

    public void setSequnce(Group sequnce) {
        this.sequence = sequence;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
