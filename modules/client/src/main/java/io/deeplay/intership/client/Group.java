import java.util.HashSet;
import java.util.Set;

public class Group {
    private Set<Stone> stones;
    private Set<Stone> freeCells;
    Group(){
        stones = new HashSet<>();
        freeCells = new HashSet<>();
    }
    void addStone(Stone stone){
        stones.add(stone);
    }
    void addDame(Stone stone){
        freeCells.add(stone);
    }
    void removeDame(Stone stone){
        freeCells.remove(stone);
    }
    public int getCountOfDame(){
        return freeCells.size();
    }
    public Set<Stone> getFreeCells(){
        return freeCells;
    }
    public int getCountOfStones(){
        return stones.size();
    }
}
