package io.deeplay.intership.bot;

import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GameId;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;

import java.util.ArrayList;
import java.util.List;

public class RandomBot implements DecisionMaker {
    private final String token;
    private final Color color;
    private final Stone[][] gameField = null;

    public RandomBot(String token, Color color) {
        this.token = token;
        this.color = color;
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
    public LoginPassword getLoginPassword() throws ClientException {
        return null;
    }


    @Override
    public GameId getGameId() throws ClientException {
        return null;
    }

    @Override
    public Color getColor() throws ClientException {
        return null;
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

    private GameAction skipTurn() {
        return new GameAction(RequestType.PASS, 0, 0);
    }
}
