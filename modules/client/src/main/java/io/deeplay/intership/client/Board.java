import java.util.*;

public class Board {
    private static final int sizeOfBoard = 9;
    private Stone board[][] = new Stone[sizeOfBoard][sizeOfBoard];
    private Set<Group> setOfGroups;
    Board(){
        setOfGroups = new HashSet<>();
        for(int y = 0; y < sizeOfBoard; y++){
            for(int x = 0; x < sizeOfBoard; x++){
                board[y][
                        x] = new Stone(x, y);
            }
        }
    }

    public void setStone(int x, int y, Color color){
        board[y][x].setColor(color);
    }

    public Set<Group> getSetOfGroups(){
        return setOfGroups;
    }

    boolean isEmpty(int x, int y){
        return board[y][x].getColor() == Color.EMPTY;
    }

    boolean hasOnlyOneDame(int x, int y){
        return board[y][x].getSequence().getCountOfDame() == 1;
    }

    public boolean isColor(int x, int y, Color color){
        return board[y][x].getColor() == color;
    }

    public boolean isSurrounded(int x, int y, Color color){
        BitSet sides = new BitSet(4);
        if(y > 0){
            sides.set(0, isColor(x, y-1, color));
        }
        else if(x < 8){
            sides.set(1, isColor(x+1, y, color));
        }
        else if(y < 8){
            sides.set(2, isColor(x, y+1, color));
        }
        else if(x > 0){
            sides.set(3, isColor(x-1, y, color));
        }
        return sides.cardinality() == 4;
    }
    public boolean isSuicide(int x, int y, Color color){
        boolean checkOnlyOneDame = hasOnlyOneDame(x, y);
        Color anotherColor = Color.values()[(color.ordinal() + 1) % 2];
        if(isSurrounded(x, y, color) && checkOnlyOneDame){
            return true;
        }
        else if(isSurrounded(x, y, anotherColor) && !checkOnlyOneDame){
            return true;
        }
        return false;
    }

    // Проверка корректности хода
    public boolean isCorrectMove(int x, int y, Color color){
        if(isEmpty(x, y) && !isSuicide(x, y, color)){
            return true;
        }
        return false;
    }
    public Stone[][] getBoard(){
        return board;
    }
    public Stone getStone(Cell cell){
        return board[cell.getY()][cell.getY()];
    }
}
