package io.deeplay.intership.service;

import java.util.HashSet;
import java.util.Set;

public class Validation {
    public final Board board;
    private Stone[][] field;
    public Validation(Board board){
        this.board = board;
        field = board.getField();
    }

    private Set<Stone> getNearStones(Color color, int x, int y){
        Set<Stone> nearStones = new HashSet<>();
        if (x > 0 && field[x-1][y].getColor() == color){
            nearStones.add(field[x-1][y]);
        }
        if (y > 0 && field[x][y-1].getColor() == color){
            nearStones.add(field[x][y-1]);
        }
        if (x < board.getField().length && field[x+1][y].getColor() == color){
            nearStones.add(field[x+1][y]);
        }
        if (y < board.getField().length && field[x][y+1].getColor() == color){
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
        return !isSuicide(color, x, y);
    }
}
