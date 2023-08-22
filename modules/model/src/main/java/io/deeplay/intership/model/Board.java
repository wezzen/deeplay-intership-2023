package io.deeplay.intership.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, представляющий игровую доску в игре Го. Доска состоит из двумерного массива камней
 * {@link Stone} и групп камней {@link Group}, которые представляют объединенные камней одного
 * цвета, примыкающие друг к другу по горизонтали и/или вертикали.
 */
public class Board {
    public static final int DEFAULT_BOARD_SIZE = 9;
    private final Stone[][] field;
    @Deprecated
    private final Set<Group> groups;
    private Stone lastBlackMove;
    private Stone lastWhiteMove;

    /**
     * Конструктор класса Board. Инициализирует двумерный массив бесцветными камнями {@link Stone}.
     * Множество групп камней инициализируется через {@link HashSet}.
     */
    public Board() {
        field = new Stone[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Stone(Color.EMPTY, i, j);
            }
        }
        groups = new HashSet<>();
    }

    public Stone[][] getField() {
        return field;
    }

    @Deprecated
    public Set<Group> getGroups() {
        return groups;
    }

    @Deprecated
    public void addGroup(Group group) {
        groups.add(group);
    }

    @Deprecated
    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public void updateLastMoveState(Stone stone) {
        switch (stone.getColor()) {
            case BLACK -> lastBlackMove = stone;
            case WHITE -> lastWhiteMove = stone;
            default -> throw new IllegalStateException();
        }
    }

    public void resetLastMoveState(Color color) {
        switch (color) {
            case BLACK -> lastBlackMove = null;
            case WHITE -> lastWhiteMove = null;
            default -> throw new IllegalStateException();
        }
    }

    public Stone getLastMoveByColor(Color color) {
        return switch (color) {
            case BLACK -> lastBlackMove;
            case WHITE -> lastWhiteMove;
            default -> throw new IllegalStateException();
        };
    }
}
