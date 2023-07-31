
package io.deeplay.inership.server;
import java.util.Set;

/**
 * Класс описывающий доску Гобан размером 9х9
 */
public class Board {
    /**
     * Ширина
     */
    private final int WEIGHT = 9;

    /** Высота */
    private final int HEIGHT = 9;
    /** Поле игры*/
    private Stone[][] field;

    /** Множество групп камней */
    private Set<Group> stoneGroups;

    /**
     * Установить камень на позицию
     *
     * @param xPosition  (1..9)
     * @param yPosition  (1..9)
     * @param stoneColor Цвет камня
     * @return true если поставил камень
     */
    public boolean setStone(int xPosition, int yPosition, Color stoneColor) {
        boolean answer;
        if (isEmptyCell(xPosition, yPosition)) {
            answer = true;
            field[xPosition][yPosition].setColor(stoneColor);
        } else {
            answer = false;
        }
        return answer;
    }

    /**
     * Проверить свободное место на позиции
     *
     * @param xPosition (1..9)
     * @param yPosition (1..9)
     * @return true если не вышли за границы и место свободно
     */
    private boolean isEmptyCell(int xPosition, int yPosition) {
        return (xPosition <= HEIGHT && xPosition >= 1) && (yPosition < WEIGHT && xPosition >= 1) && (field[xPosition][yPosition].getColor() == Color.EMPTY);
    }
}
