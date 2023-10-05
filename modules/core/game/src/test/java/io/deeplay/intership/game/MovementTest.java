package io.deeplay.intership.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Group;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.validation.Validation;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class MovementTest {
    private Board board;
    private Validation validation;

    public void setInitialStones(List<Stone> initialStones) {
        board = new Board();
        Stone[][] field = board.getField();
        for (Stone stone : initialStones) {
            field[stone.getRowNumber()][stone.getColumnNumber()] = stone;
        }
        validation = new Validation(board);
    }

    public boolean test1(){
        List<Stone> testStones = new ArrayList<>();
        testStones.add(new Stone(Color.BLACK, 0, 2, new Group()));
        testStones.add(new Stone(Color.BLACK, 1, 3, new Group()));
        testStones.add(new Stone(Color.BLACK, 2, 4, new Group()));
        testStones.add(new Stone(Color.BLACK, 1, 5));
        testStones.add(new Stone(Color.BLACK, 0, 5));
        testStones.add(new Stone(Color.WHITE, 0, 3, new Group()));
        testStones.add(new Stone(Color.WHITE, 1, 4, new Group()));
        setInitialStones(testStones);
        Stone[][] field = board.getField();

        Group groupTuple = new Group();
        testStones.get(3).setGroup(groupTuple);
        testStones.get(4).setGroup(groupTuple);
        testStones.get(3).getGroup().addStone(testStones.get(3));
        testStones.get(3).getGroup().addStone(testStones.get(4));
        testStones.get(3).getGroup().addFreeCell(field[0][6]);
        testStones.get(3).getGroup().addFreeCell(field[1][6]);
        testStones.get(3).getGroup().addFreeCell(field[2][5]);

        testStones.get(5).getGroup().addStone(testStones.get(5));
        testStones.get(5).getGroup().addFreeCell(field[0][4]);
        testStones.get(6).getGroup().addStone(testStones.get(6));
        testStones.get(6).getGroup().addFreeCell(field[0][4]);

        return validation.isCorrectMove(Color.WHITE, 0, 4);
    }

    public boolean test2(){
        List<Stone> testStones = new ArrayList<>();
        testStones.add(new Stone(Color.BLACK, 1, 1, new Group()));
        testStones.add(new Stone(Color.BLACK, 2, 2, new Group()));
        testStones.add(new Stone(Color.BLACK, 1, 3));
        testStones.add(new Stone(Color.BLACK, 0, 3));
        testStones.add(new Stone(Color.WHITE, 0, 1, new Group()));
        testStones.add(new Stone(Color.WHITE, 1, 2, new Group()));
        setInitialStones(testStones);
        Stone[][] field = board.getField();

        Group groupTuple = new Group();
        testStones.get(2).setGroup(groupTuple);
        testStones.get(3).setGroup(groupTuple);
        testStones.get(2).getGroup().addStone(testStones.get(2));
        testStones.get(2).getGroup().addStone(testStones.get(3));
        testStones.get(2).getGroup().addFreeCell(field[0][4]);
        testStones.get(2).getGroup().addFreeCell(field[1][4]);
        testStones.get(2).getGroup().addFreeCell(field[2][3]);

        testStones.get(4).getGroup().addStone(testStones.get(4));
        testStones.get(4).getGroup().addFreeCell(field[0][0]);
        testStones.get(4).getGroup().addFreeCell(field[0][2]);
        testStones.get(5).getGroup().addStone(testStones.get(5));
        testStones.get(5).getGroup().addFreeCell(field[0][2]);

        return validation.isCorrectMove(Color.WHITE, 0, 2);
    }

    public boolean test3(){
        List<Stone> testStones = new ArrayList<>();
        testStones.add(new Stone(Color.BLACK, 0, 1));
        testStones.add(new Stone(Color.BLACK, 1, 1));
        testStones.add(new Stone(Color.BLACK, 2, 0, new Group()));
        setInitialStones(testStones);
        Stone[][] field = board.getField();

        Group groupTuple = new Group();
        testStones.get(0).setGroup(groupTuple);
        testStones.get(1).setGroup(groupTuple);
        testStones.get(0).getGroup().addStone(testStones.get(0));
        testStones.get(0).getGroup().addStone(testStones.get(1));
        testStones.get(0).getGroup().addFreeCell(field[0][2]);
        testStones.get(0).getGroup().addFreeCell(field[1][2]);
        testStones.get(0).getGroup().addFreeCell(field[2][1]);
        testStones.get(0).getGroup().addFreeCell(field[0][0]);
        testStones.get(0).getGroup().addFreeCell(field[1][0]);

        testStones.get(2).getGroup().addStone(testStones.get(2));
        testStones.get(2).getGroup().addFreeCell(field[1][0]);
        testStones.get(2).getGroup().addFreeCell(field[2][1]);
        testStones.get(2).getGroup().addFreeCell(field[3][0]);

        return validation.isCorrectMove(Color.WHITE, 1, 0);
    }

    public boolean test4(){
        List<Stone> testStones = new ArrayList<>();
        testStones.add(new Stone(Color.BLACK, 0, 2, new Group()));
        testStones.add(new Stone(Color.BLACK, 1, 1, new Group()));
        testStones.add(new Stone(Color.BLACK, 1, 3, new Group()));
        testStones.add(new Stone(Color.BLACK, 2, 0, new Group()));
        testStones.add(new Stone(Color.BLACK, 2, 4, new Group()));
        testStones.add(new Stone(Color.BLACK, 3, 1, new Group()));
        testStones.add(new Stone(Color.BLACK, 3, 3, new Group()));
        testStones.add(new Stone(Color.BLACK, 4, 2, new Group()));

        testStones.add(new Stone(Color.WHITE, 1, 2, new Group()));
        testStones.add(new Stone(Color.WHITE, 2, 1, new Group()));
        testStones.add(new Stone(Color.WHITE, 2, 3, new Group()));
        testStones.add(new Stone(Color.WHITE, 3, 2, new Group()));
        setInitialStones(testStones);
        Stone[][] field = board.getField();

        testStones.get(8).getGroup().addStone(testStones.get(8));
        testStones.get(8).getGroup().addFreeCell(field[2][2]);
        testStones.get(9).getGroup().addStone(testStones.get(9));
        testStones.get(9).getGroup().addFreeCell(field[2][2]);
        testStones.get(10).getGroup().addStone(testStones.get(10));
        testStones.get(10).getGroup().addFreeCell(field[2][2]);
        testStones.get(11).getGroup().addStone(testStones.get(11));
        testStones.get(11).getGroup().addFreeCell(field[2][2]);

        return validation.isCorrectMove(Color.WHITE, 2, 2);
    }

    public boolean test5(){
        List<Stone> testStones = new ArrayList<>();
        testStones.add(new Stone(Color.BLACK, 1, 2, new Group()));
        testStones.add(new Stone(Color.BLACK, 2, 1, new Group()));
        testStones.add(new Stone(Color.BLACK, 2, 3, new Group()));
        testStones.add(new Stone(Color.BLACK, 3, 2, new Group()));

        testStones.add(new Stone(Color.WHITE, 0, 2, new Group()));
        testStones.add(new Stone(Color.WHITE, 1, 1, new Group()));
        testStones.add(new Stone(Color.WHITE, 1, 3, new Group()));
        testStones.add(new Stone(Color.WHITE, 2, 0, new Group()));
        testStones.add(new Stone(Color.WHITE, 2, 4, new Group()));
        testStones.add(new Stone(Color.WHITE, 3, 1, new Group()));
        testStones.add(new Stone(Color.WHITE, 3, 3, new Group()));
        testStones.add(new Stone(Color.WHITE, 4, 2, new Group()));
        setInitialStones(testStones);
        Stone[][] field = board.getField();

        testStones.get(0).getGroup().addStone(testStones.get(0));
        testStones.get(0).getGroup().addFreeCell(field[2][2]);
        testStones.get(1).getGroup().addStone(testStones.get(1));
        testStones.get(1).getGroup().addFreeCell(field[2][2]);
        testStones.get(2).getGroup().addStone(testStones.get(2));
        testStones.get(2).getGroup().addFreeCell(field[2][2]);
        testStones.get(3).getGroup().addStone(testStones.get(3));
        testStones.get(3).getGroup().addFreeCell(field[2][2]);

        return validation.isCorrectMove(Color.WHITE, 2, 2);
    }

    @Test
    public void testCorrectMovement() {
        assertEquals(test1(), false);
        assertEquals(test2(), true);
        assertEquals(test3(), true);
        assertEquals(test4(), false);
        assertEquals(test5(), true);
    }
}
