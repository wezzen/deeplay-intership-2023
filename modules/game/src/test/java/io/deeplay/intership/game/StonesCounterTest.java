package io.deeplay.intership.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import org.junit.jupiter.api.Test;

public class StonesCounterTest {
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
        StonesCounter stonesCounter = new StonesCounter(board.getField());
        stonesCounter.countCapturedEmptyStones();
        assertEquals(6, stonesCounter.getBlackPoints());
        assertEquals(8, stonesCounter.getWhitePoints());
    }
}
