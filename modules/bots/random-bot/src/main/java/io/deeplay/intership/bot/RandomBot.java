package io.deeplay.intership.bot;

import io.deeplay.intership.model.Move;
import io.deeplay.intership.action.PlayerActions;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;

import java.util.ArrayList;
import java.util.List;

public class RandomBot implements PlayerActions {
    private final String token;
    private final Color color;

    public RandomBot(String token, Color color) {
        this.token = token;
        this.color = color;
    }

    @Override
    public Move chooseGameAction(final Stone[][] gameField) {
        boolean canMove = false;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length && !canMove; j++) {
                Stone stone = gameField[i][j];
                if (stone.getColor() == Color.EMPTY) {
                    canMove = true;
                }
            }
        }
        if (canMove) {
            return makeMove(gameField);
        } else {
            return skipTurn();
        }
    }

    @Override
    public Move makeMove(final Stone[][] gameField) {
        List<Stone> emptyStones = new ArrayList<>();
        Stone[][] field = gameField;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                Stone stone = field[i][j];
                if (stone.getColor() == Color.EMPTY) {
                    emptyStones.add(stone);
                }
            }
        }
        Stone stone = emptyStones.get((int) (Math.random() * emptyStones.size()));
        return new Move(
                token,
                color.name(),
                stone.getRowNumber(),
                stone.getColumnNumber()
        );

    }

    @Override
    public Move skipTurn() {
        return new Move(token, Color.EMPTY.name(), 0, 0);
    }

    @Override
    public Color chooseColor() {
        return null;
    }

    @Override
    public void endGame(String gameResult) {

    }
}
