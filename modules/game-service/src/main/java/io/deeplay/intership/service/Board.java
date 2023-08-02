package io.deeplay.intership.service;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, представляющий игровую доску в игре Го. Доска состоит из двумерного массива камней
 * {@link Stone} и групп камней {@link Group}, которые представляют объединенные камней одного
 * цвета, примыкающие друг к другу по горизонтали и/или вертикали.
 */
public class Board {
    private static final int DEFAULT_BOARD_SIZE = 9;
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

    private Set<Stone> getNearStones(Color color, int x, int y){
        Set<Stone> nearStones = new HashSet<>();
        if (x > 0 && field[x-1][y].getColor() == color){
            nearStones.add(field[x-1][y]);
        }
        if (y > 0 && field[x][y-1].getColor() == color){
            nearStones.add(field[x][y-1]);
        }
        if (x < 8 && field[x+1][y].getColor() == color){
            nearStones.add(field[x+1][y]);
        }
        if (y < 8 && field[x][y+1].getColor() == color){
            nearStones.add(field[x][y+1]);
        }
        return nearStones;
    }

    private boolean isSuicide(Color color, int x, int y) {
        Color enemyColor = Color.values()[(color.ordinal()+1)%2];
        Set<Stone> friendStones = getNearStones(color, x, y);
        Set<Stone> enemyStones = getNearStones(enemyColor, x, y);
        if(friendStones.isEmpty()){
            for(Stone enemyStone : enemyStones){
                if(enemyStone.getGroup().getCountOfFreeDames() < 2){
                    return false;
                }
            }
            return true;
        }
        for(Stone friendStone : friendStones){
            if(friendStone.getGroup().getCountOfFreeDames() > 1){
                return false;
            }
        }
        return true;
    }

    public boolean isCorrectMove(Color color, int x, int y) {
        Set<Stone> emptyStones = getNearStones(Color.EMPTY, x, y);
        if(field[x][y].getColor() != Color.EMPTY){
            return false;
        }
        else if(!emptyStones.isEmpty()){
            return true;
        }
        else {
            return !isSuicide(color, x, y);
        }
    }

    public Stone[][] getField() {
        return field;
    }

    public Set<Group> getGroups() {
        return groups;
    }
}
