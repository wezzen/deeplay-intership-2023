package io.deeplay.intership.service;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, представляющий игровую доску в игре Го. Доска состоит из двумерного массива камней
 * {@link Stone} и групп камней {@link Group}, которые представляют объединенные камней одного
 * цвета, примыкающие друг к другу по горизонтали и/или вертикали.
 */
public class Board {
    public static final int DEFAULT_BOARD_SIZE = 9;
    private Stone[][] field;
    private Set<Group> groups;

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

    public Set<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group){
        groups.add(group);
    }
    public void removeGroup(Group group){
        groups.remove(group);
    }
}
