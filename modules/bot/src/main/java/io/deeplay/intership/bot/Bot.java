package io.deeplay.intership.bot;

import io.deeplay.intership.game.Board;
import io.deeplay.intership.game.Color;
import io.deeplay.intership.game.Stone;
import io.deeplay.intership.ui.terminal.PlayerActions;

import java.util.ArrayList;
import java.util.List;

public class Bot implements PlayerActions {

    @Override
    public Stone chooseGameAction(Board board) {
        Stone[][] field = board.getField();
        boolean canMove = false;
        for (int i = 0; i < board.DEFAULT_BOARD_SIZE; i++) {
            for (int j = 0; j < board.DEFAULT_BOARD_SIZE && !canMove; j++) {
                Stone stone = field[i][j];
                if(stone.getColor() == Color.EMPTY){
                    canMove = true;
                }
            }
        }
        if(canMove){
            return makeMove(board);
        } else {
            return skipTurn();
        }
    }

    @Override
    public Stone makeMove(Board board) {
        List<Stone> emptyStones = new ArrayList<>();
        Stone[][] field = board.getField();
        for (int i = 0; i < board.DEFAULT_BOARD_SIZE; i++) {
            for (int j = 0; j < board.DEFAULT_BOARD_SIZE; j++) {
                Stone stone = field[i][j];
                if(stone.getColor() == Color.EMPTY){
                    emptyStones.add(stone);
                }
            }
        }
        return emptyStones.get((int)(Math.random() * emptyStones.size()));
    }

    @Override
    public Stone skipTurn() {
        return new Stone(Color.EMPTY, 0, 0);
    }

    @Override
    public Color chooseColor() {
        return null;
    }

    @Override
    public void endGame(String gameResult) {

    }
}
