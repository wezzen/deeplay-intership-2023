package io.deeplay.intership.game;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс {@code Validation} предоставляет методы для проверки ходов и игровых ситуаций на доске го.
 */
public class Validation {
    private final Board board;
    private final Stone[][] field;
    private final int MAX_FIELD_VALUE;
    private final int MIN_FIELD_VALUE;

    public Validation(Board board) {
        this.board = board;
        this.field = board.getField();
        this.MAX_FIELD_VALUE = field.length - 1;
        this.MIN_FIELD_VALUE = 0;
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
        if (x < MAX_FIELD_VALUE && field[x + 1][y].getColor() == color) {
            nearStones.add(field[x + 1][y]);
        }
        if (y < MAX_FIELD_VALUE && field[x][y + 1].getColor() == color) {
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
        Set<Stone> friendStones = getNearStones(color, x, y);
        Set<Stone> enemyStones = getNearStones(Color.invertColor(color), x, y);
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
        if (isKoSituation(color, x, y)) {
            return false;
        }
        return !isSuicide(color, x, y);
    }

    /**
     * Проверяет, представляет ли позиция ситуацию Ko, в которой повторение предыдущего хода запрещено.
     *
     * @param color цвет игрока, делающего ход
     * @param x     координата x в массиве {@code field} из {@link Board}
     * @param y     координата y в массиве {@code field} из {@link Board}
     * @return {@code true}, если ситуация Ко, иначе {@code false}
     */
    public boolean isKoSituation(Color color, int x, int y) {
        Group group = field[x][y].getGroup();
        if (group == null || group.getCountOfStones() != 1) {
            return false;
        }
        Stone previousMove = board.getLastMoveByColor(color);
        if (new Stone(color, x, y).equals(previousMove)) {
            return false;
        }

        Set<Stone> enemy = getNearStones(Color.invertColor(color), x, y);
        for (Stone stone : enemy) {
            if (stone.getGroup().getCountOfFreeDames() == 1 &&
                    stone.getGroup().getFreeCells().contains(field[x][y])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет, является ли данная группа крепостью, что означает, что у нее больше 1 свободных дамэ, окруженных
     * своей группой.
     *
     * @param group {@link Group} для проверки состояния крепости
     * @return {@code true}, если группа является крепостью, иначе {@code false}
     */
    public boolean isFortress(Group group) {
        final int freeCellsForFortress = 2;

        //проверка на необходимый минимум пустых ячеек
        final Color groupColor = group.getStones().iterator().next().getColor();
        final Set<Stone> dames = group.getFreeCells();
        int freeCellCounter = 0;
        for (Stone emptyStone : dames) {
            if (isSurroundedOneColor(emptyStone, groupColor)) {
                freeCellCounter++;

                //получаем окружающие свои камни
                Set<Stone> neighbors = getNearStones(
                        groupColor,
                        emptyStone.getRowNumber(),
                        emptyStone.getColumnNumber());

                //проверяем, могут ли эти камни быть окружены
                if (hasDifferentGroups(neighbors)) {
                    return false;
                }
            }
        }

        return freeCellCounter >= freeCellsForFortress;
    }

    /**
     * Проверяет, окружен ли данный камень камнями определенного цвета со всех четырех сторон.
     *
     * @param stone - камень, который нужно проверить на окружение
     * @param color цвет окружающих камней
     * @return {@code true}, если камень со всех сторон окружен камнями указанного цвета, иначе {@code false}
     */
    private boolean isSurroundedOneColor(Stone stone, Color color) {
        return getNearStones(
                Color.invertColor(color),
                stone.getRowNumber(),
                stone.getColumnNumber()).size() == 0 &&
                getNearStones(
                        Color.EMPTY,
                        stone.getRowNumber(),
                        stone.getColumnNumber()).size() == 0;
    }

    /**
     * Проверяет принадлежность всех камней из {@link Set} к одной группе камней.
     *
     * @param stones - {@link Set} камней, который нужно проверить
     * @return {@code false}, если все камни принадлежат одной группе, иначе {@code true}
     */
    private boolean hasDifferentGroups(Set<Stone> stones) {
        Group group = stones.iterator().next().getGroup();
        return stones
                .stream()
                .filter(stone -> stone.getGroup() == group)
                .count() != stones.size();
    }
}
