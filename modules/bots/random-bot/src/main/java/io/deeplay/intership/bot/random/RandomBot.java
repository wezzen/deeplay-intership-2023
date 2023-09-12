package io.deeplay.intership.bot.random;

import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;

import java.util.ArrayList;
import java.util.List;

public class RandomBot extends Bot {
    private Stone[][] gameField = new Board().getField();

    public RandomBot(String name, Color color) {
        super(name, color);
    }

    @Override
    public GameAction getGameAction() {
        //TODO: прикрутить валидацию на бота
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
    public Color getColor() {
        return color;
    }

    private GameAction makeMove(final Stone[][] gameField) {
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
        return new GameAction(
                RequestType.TURN,
                stone.getRowNumber(),
                stone.getColumnNumber()
        );

    }

    @Override
    public void startGame() {

    }

    @Override
    public void endGame() {

    }

    private GameAction skipTurn() {
        return new GameAction(RequestType.PASS, 0, 0);
    }

    public void setGameField(final Stone[][] gameField) {
        this.gameField = gameField;
    }
}
