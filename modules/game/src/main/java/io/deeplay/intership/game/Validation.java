package io.deeplay.intership.game;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс {@code Validation} предоставляет методы для проверки ходов и игровых ситуаций на доске го.
 */
public class Validation {
    public final Board board;
    private final Stone[][] field;
    private final int FIELD_SIZE;

    public Validation(Board board) {
        this.board = board;
        this.field = board.getField();
        this.FIELD_SIZE = field.length;
    }

    /**
     * Получает набор камней, соседних с указанной позицией, с заданным цветом.
     *
     * @param color цвет соседних камней для поиска
     * @param x     координата номера строки в двумерном массиве {@link Stone}
     * @param y     координата номера столбца в двумерном массиве {@link Stone}
     * @return набор {@code Set} соседних камней указанного цвета {@code Color}
     */
    private Set<Stone> getNearStones(Color color, int x, int y) {
        Set<Stone> nearStones = new HashSet<>();
        if (x > 0 && field[x - 1][y].getColor() == color) {
            nearStones.add(field[x - 1][y]);
        }
        if (y > 0 && field[x][y - 1].getColor() == color) {
            nearStones.add(field[x][y - 1]);
        }
        if (x < FIELD_SIZE && field[x + 1][y].getColor() == color) {
            nearStones.add(field[x + 1][y]);
        }
        if (y < FIELD_SIZE && field[x][y + 1].getColor() == color) {
            nearStones.add(field[x][y + 1]);
        }
        return nearStones;
    }

    /**
     * Определяет, считается ли размещение камня в указанной позиции самоубийственным ходом.
     *
     * @param color цвет размещаемого камня
     * @param x     координата номера строки в двумерном массиве {@link Stone}
     * @param y     координата номера столбца в двумерном массиве {@link Stone}
     * @return {@code true}, если ход самоубийственный, иначе {@code false}
     */
    private boolean isSuicide(Color color, int x, int y) {
        Color enemyColor = Color.values()[(color.ordinal() + 1) % 2];
        Set<Stone> friendStones = getNearStones(color, x, y);
        Set<Stone> enemyStones = getNearStones(enemyColor, x, y);
        if (friendStones.isEmpty()) {
            for (Stone enemyStone : enemyStones) {
                if (enemyStone.getGroup().getCountOfFreeDames() < 2) {
                    return false;
                }
            }
            return true;
        }
        for (Stone friendStone : friendStones) {
            if (friendStone.getGroup().getCountOfFreeDames() > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверяет правильность хода для данного цвета и позиции на доске.
     *
     * @param color цвет камня, делающего ход
     * @param x     координата перемещения по горизонтали поля {@code field} из класса {@link Board}
     * @param y     координата перемещения по вертикали поля {@code field} из класса {@link Board}
     * @return {@code true}, если ход правильный, иначе {@code false}
     */
    public boolean isCorrectMove(Color color, int x, int y) {
        if (field[x][y].getColor() != Color.EMPTY) {
            return false;
        }

        Set<Stone> emptyStones = getNearStones(Color.EMPTY, x, y);
        if (!emptyStones.isEmpty()) {
            return true;
        }
        return !isSuicide(color, x, y);
    }
}
