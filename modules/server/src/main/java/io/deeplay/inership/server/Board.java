
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
                field[i][j] = new Stone(Color.EMPTY, i, j, null);
            }
        }
        stoneGroups = new HashSet<>();
    }

    public int getSize() {
        return SIZE;
    }

    /**
     * Метод добавляющий камень на доску
     * @param xPosition (0..8)
     * @param yPosition (0..8)
     * @param stoneColor Цвет камня
     * @return true -- если успешно
     */
    public boolean setStone(int xPosition, int yPosition, Color stoneColor) {
        boolean answer = isEmptyCell(xPosition, yPosition);
        if (answer) {
            field[xPosition][yPosition].setColor(stoneColor);
        }
        return answer;
    }

    /**
     * Метод проверяет свободное место на позиции
     * @param xPosition (0..8)
     * @param yPosition (0..8)
     * @return true -- если есть свободное место
     */
    public boolean isEmptyCell(int xPosition, int yPosition) {
        return isInBoard(xPosition, yPosition) &&
                (field[xPosition][yPosition].getColor() == Color.EMPTY);
    }

    private boolean isInBoard(int xPosition, int yPosition) {
        return (xPosition <= (SIZE - 1) && xPosition >= 0) &&
                (yPosition <= (SIZE - 1) && yPosition >= 0);
    }
}
