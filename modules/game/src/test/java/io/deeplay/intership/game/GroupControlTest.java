package io.deeplay.intership.game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupControlTest {
    private Board board;
    private GroupControl groupControl;

    public void setInitialStones(List<Stone> initialStones) {
        board = new Board();
        Stone[][] field = board.getField();
        for (Stone stone : initialStones) {
            field[stone.getRowNumber()][stone.getColumnNumber()] = stone;
        }
        groupControl = new GroupControl(board);
    }

    public boolean test1() {
        List<Stone> testStones = new ArrayList<>();
        testStones.add(new Stone(Color.BLACK, 2, 1, new Group()));
        testStones.add(new Stone(Color.BLACK, 2, 3, new Group()));
        testStones.add(new Stone(Color.BLACK, 3, 2, new Group()));
        testStones.add(new Stone(Color.WHITE, 1, 1));
        testStones.add(new Stone(Color.WHITE, 1, 2));
        testStones.add(new Stone(Color.WHITE, 1, 3));
        setInitialStones(testStones);
        Stone[][] field = board.getField();

        Group groupTriple = new Group();
        groupTriple.addStone(testStones.get(3));
        groupTriple.addStone(testStones.get(4));
        groupTriple.addStone(testStones.get(5));

        testStones.get(0).getGroup().addStone(testStones.get(0));
        testStones.get(0).getGroup().addFreeCell(field[2][2]);
        testStones.get(0).getGroup().addFreeCell(field[2][0]);
        testStones.get(0).getGroup().addFreeCell(field[3][1]);
        testStones.get(1).getGroup().addStone(testStones.get(1));
        testStones.get(1).getGroup().addFreeCell(field[2][2]);
        testStones.get(1).getGroup().addFreeCell(field[2][4]);
        testStones.get(1).getGroup().addFreeCell(field[3][3]);
        testStones.get(2).getGroup().addStone(testStones.get(2));
        testStones.get(2).getGroup().addFreeCell(field[2][2]);
        testStones.get(2).getGroup().addFreeCell(field[3][1]);
        testStones.get(2).getGroup().addFreeCell(field[4][2]);
        testStones.get(2).getGroup().addFreeCell(field[3][3]);

        testStones.get(3).setGroup(groupTriple);
        testStones.get(4).setGroup(groupTriple);
        testStones.get(5).setGroup(groupTriple);

        groupTriple.addFreeCell(field[0][1]);
        groupTriple.addFreeCell(field[0][2]);
        groupTriple.addFreeCell(field[0][3]);
        groupTriple.addFreeCell(field[1][0]);
        groupTriple.addFreeCell(field[1][4]);
        groupTriple.addFreeCell(field[2][2]);

        field[2][2].setColor(Color.WHITE);
        groupControl.setGroup(field[2][2]);
        return field[2][2].getGroup() == field[1][1].getGroup();
    }

    public boolean test2() {
        List<Stone> testStones = new ArrayList<>();
        testStones.add(new Stone(Color.BLACK, 0, 1, new Group()));
        testStones.add(new Stone(Color.BLACK, 1, 2, new Group()));
        testStones.add(new Stone(Color.BLACK, 2, 1, new Group()));
        setInitialStones(testStones);
        Stone[][] field = board.getField();

        testStones.get(0).getGroup().addStone(testStones.get(0));
        testStones.get(1).getGroup().addStone(testStones.get(1));
        testStones.get(2).getGroup().addStone(testStones.get(2));
        testStones.get(0).getGroup().addFreeCell(field[0][0]);
        testStones.get(0).getGroup().addFreeCell(field[0][2]);
        testStones.get(0).getGroup().addFreeCell(field[1][1]);
        testStones.get(1).getGroup().addFreeCell(field[0][2]);
        testStones.get(1).getGroup().addFreeCell(field[2][2]);
        testStones.get(1).getGroup().addFreeCell(field[1][3]);
        testStones.get(1).getGroup().addFreeCell(field[1][1]);
        testStones.get(2).getGroup().addFreeCell(field[1][1]);

        field[1][1].setColor(Color.WHITE);
        groupControl.setGroup(field[1][1]);
        return field[1][1].getGroup() != null;
    }

    public boolean test3() {
        List<Stone> testStones = new ArrayList<>();
        testStones.add(new Stone(Color.BLACK, 1, 1, new Group()));
        testStones.add(new Stone(Color.WHITE, 1, 0, new Group()));
        testStones.add(new Stone(Color.WHITE, 0, 1));
        testStones.add(new Stone(Color.WHITE, 0, 2));
        setInitialStones(testStones);
        Stone[][] field = board.getField();

        Group groupTuple = new Group();
        testStones.get(0).getGroup().addStone(testStones.get(0));
        testStones.get(0).getGroup().addFreeCell(field[2][1]);
        testStones.get(0).getGroup().addFreeCell(field[1][2]);
        testStones.get(1).getGroup().addStone(testStones.get(1));
        testStones.get(1).getGroup().addFreeCell(field[0][0]);
        testStones.get(1).getGroup().addFreeCell(field[2][0]);

        testStones.get(2).setGroup(groupTuple);
        testStones.get(3).setGroup(groupTuple);
        testStones.get(2).getGroup().addStone(testStones.get(2));
        testStones.get(2).getGroup().addStone(testStones.get(3));
        testStones.get(2).getGroup().addFreeCell(field[0][0]);
        testStones.get(2).getGroup().addFreeCell(field[1][2]);
        testStones.get(2).getGroup().addFreeCell(field[0][3]);

        field[0][0].setColor(Color.WHITE);
        groupControl.setGroup(field[0][0]);
        return field[0][2].getGroup() == field[0][0].getGroup() &&
                field[0][2].getGroup() == field[1][0].getGroup();
    }

    public boolean test4() {
        List<Stone> testStones = new ArrayList<>();
        testStones.add(new Stone(Color.BLACK, 2, 0, new Group()));
        testStones.add(new Stone(Color.BLACK, 3, 1, new Group()));
        testStones.add(new Stone(Color.BLACK, 1, 3, new Group()));

        testStones.add(new Stone(Color.WHITE, 0, 1, new Group()));
        testStones.add(new Stone(Color.WHITE, 1, 0, new Group()));
        testStones.add(new Stone(Color.WHITE, 1, 2, new Group()));
        testStones.add(new Stone(Color.WHITE, 2, 1, new Group()));
        setInitialStones(testStones);
        Stone[][] field = board.getField();

        testStones.get(0).getGroup().addStone(testStones.get(0));
        testStones.get(1).getGroup().addStone(testStones.get(1));
        testStones.get(2).getGroup().addStone(testStones.get(2));
        testStones.get(3).getGroup().addStone(testStones.get(3));
        testStones.get(4).getGroup().addStone(testStones.get(4));
        testStones.get(5).getGroup().addStone(testStones.get(5));
        testStones.get(6).getGroup().addStone(testStones.get(6));

        testStones.get(3).getGroup().addFreeCell(field[0][0]);
        testStones.get(3).getGroup().addFreeCell(field[0][2]);
        testStones.get(4).getGroup().addFreeCell(field[0][0]);
        testStones.get(4).getGroup().addFreeCell(field[2][0]);
        testStones.get(5).getGroup().addFreeCell(field[0][2]);
        testStones.get(5).getGroup().addFreeCell(field[2][2]);
        testStones.get(6).getGroup().addFreeCell(field[2][2]);

        field[1][1].setColor(Color.WHITE);
        groupControl.setGroup(field[1][1]);
        return field[0][1].getGroup() == field[1][0].getGroup() &&
                field[2][1].getGroup() == field[1][0].getGroup() &&
                field[1][2].getGroup() == field[2][1].getGroup() &&
                field[1][1].getGroup() == field[0][1].getGroup();
    }

    public boolean test5() {
        setInitialStones(new ArrayList<Stone>());
        Stone[][] field = board.getField();

        field[0][2].setColor(Color.WHITE);
        groupControl.setGroup(field[0][2]);
        return field[0][2].getGroup() != null;
    }

    public boolean test6() {
        List<Stone> testStones = new ArrayList<>();
        testStones.add(new Stone(Color.BLACK, 1, 1));
        testStones.add(new Stone(Color.BLACK, 1, 2));

        testStones.add(new Stone(Color.WHITE, 1, 0, new Group()));
        testStones.add(new Stone(Color.WHITE, 0, 2, new Group()));
        testStones.add(new Stone(Color.WHITE, 2, 1));
        testStones.add(new Stone(Color.WHITE, 2, 2));
        setInitialStones(testStones);
        Stone[][] field = board.getField();

        Group tuple1 = new Group();
        Group tuple2 = new Group();
        tuple1.addStone(field[1][1]);
        tuple1.addStone(field[1][2]);
        tuple2.addStone(field[2][1]);
        tuple2.addStone(field[2][2]);
        field[1][1].setGroup(tuple1);
        field[1][2].setGroup(tuple1);
        field[2][1].setGroup(tuple2);
        field[2][2].setGroup(tuple2);

        field[1][0].getGroup().addFreeCell(field[0][0]);
        field[1][0].getGroup().addFreeCell(field[2][0]);
        field[0][2].getGroup().addFreeCell(field[0][1]);
        field[0][2].getGroup().addFreeCell(field[0][3]);
        field[2][1].getGroup().addFreeCell(field[2][0]);
        field[2][1].getGroup().addFreeCell(field[2][3]);
        field[2][1].getGroup().addFreeCell(field[3][1]);
        field[2][1].getGroup().addFreeCell(field[3][2]);
        field[1][1].getGroup().addFreeCell(field[0][1]);
        field[1][1].getGroup().addFreeCell(field[1][3]);

        field[0][1].setColor(Color.WHITE);
        groupControl.removeGroup(field[0][1]);
        return field[1][1].getColor() == Color.BLACK &&
                field[1][2].getColor() == Color.BLACK;
    }

    @Test
    public void testSetGroup() {
        assertEquals(test1(), true);
        assertEquals(test2(), true);
        assertEquals(test3(), true);
        assertEquals(test4(), true);
        assertEquals(test5(), true);
    }

    @Test
    public void testRemoveGroup() {
        assertEquals(test6(), true);
    }
}