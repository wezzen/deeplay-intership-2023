
package io.deeplay.inership.server;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс описывающий доску Гобан размером 9х9
 */
public class Board {
    /**
     * Ширина
     */
    private static final int SIZE = 9;
    /** Поле игры*/
    private Stone[][] field;

    /** Множество групп камней */
    private Set<Group> stoneGroups;

    public Board() {
        field = new Stone[SIZE][SIZE];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Stone(Color.EMPTY, new Cell(i, j), null);
            }
        }
        stoneGroups = new HashSet<>();
    }

    public int getSize() {
        return SIZE;
    }
    /**
     * Установить камень на позицию
     *
     * @param cell Ячейка (0..8, 0..8)
     * @param stoneColor Цвет камня
     * @return true - если камень поставлен
     */
    public boolean setStone(Cell cell, Color stoneColor) {
        boolean answer;
        if (isEmptyCell(cell)) {
            answer = true;
            field[cell.getX()][cell.getY()].setColor(stoneColor);
        } else {
            answer = false;
        }
        return answer;
    }

    /**
     *
     * @param cell Ячейка (0..8, 0..8)
     * @return true - если ячейка пустая и в границах доски
     */
    public boolean isEmptyCell(Cell cell) {
        return isInBoard(cell) && (field[cell.getX()][cell.getY()].getColor() == Color.EMPTY);
    }

    private boolean isInBoard(Cell cell) {
        return (cell.getX() <= (SIZE - 1) && cell.getX() >= 0) && (cell.getY() <= (SIZE - 1) && cell.getY() >= 0);
    }
}
