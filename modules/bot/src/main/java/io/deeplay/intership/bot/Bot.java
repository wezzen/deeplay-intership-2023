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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Stone stone = field[i][j];
                if(stone.getColor() == Color.EMPTY){
                    emptyStones.add(stone);
                }
            }
        }
        int index = (int)(Math.random() * emptyStones.size());
        return emptyStones.get(index);
        //return
        //String.format("%d %d", (int)(Math.random() * 9 + 1),(int)(Math.random() * 9 + 1));
    }
}
