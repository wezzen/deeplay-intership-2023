package io.deeplay.intership.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CounterOfStonesTest {
    public void setField(String s, Board board){
        Stone[][] field = board.getField();
        int sizeOfField = field.length;
        int strSize = s.length();
        for(int i = 0; i < strSize; i++){
            if(s.charAt(i) == 'W'){
                field[i / sizeOfField][i % sizeOfField].setColor(Color.WHITE);
            }
            else if(s.charAt(i) == 'B'){
                field[i / sizeOfField][i % sizeOfField].setColor(Color.BLACK);
            }
            else{
                field[i / sizeOfField][i % sizeOfField].setColor(Color.EMPTY);
            }
        }
    }

    @Test
    public void testCountCapturedEmptyStones(){
        String s = "EEBEEEEWEEBEEEEWEEBBEEWWEWEEEEWEEWEWEEEEWEWEEBBEEEWEEBEBEEEEEBEEBBEEEBEEEBEEEEEEE";
        Board board = new Board();
        setField(s, board);
        CounterOfStones counterOfStones = new CounterOfStones(board);
        counterOfStones.print();
        counterOfStones.countCapturedEmptyStones();
        assertEquals(6, counterOfStones.getBlackPoints());
        assertEquals(8, counterOfStones.getWhitePoints());
    }
}
