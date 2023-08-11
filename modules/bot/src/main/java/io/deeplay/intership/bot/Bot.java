package io.deeplay.intership.bot;

import io.deeplay.intership.game.Board;
import io.deeplay.intership.game.Color;
import io.deeplay.intership.game.Stone;

import java.util.ArrayList;
import java.util.List;

public class Bot {
    public Stone randomMove(Board board){
        List<Stone> emptyStones = new ArrayList<>();
        Stone[][] field = board.getField();
        for (int i = 0; i < board.getDefaultBoardSize(); i++) {
            for (int j = 0; j < board.getDefaultBoardSize(); j++) {
                Stone stone = field[i][j];
                if(stone.getColor() == Color.EMPTY){
                    emptyStones.add(stone);
                }
            }
        }
        return emptyStones.get((int)(Math.random() * emptyStones.size()));
    }
}
