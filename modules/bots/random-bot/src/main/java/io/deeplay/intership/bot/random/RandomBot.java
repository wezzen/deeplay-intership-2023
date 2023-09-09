package io.deeplay.intership.bot.random;

import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RandomBot implements DecisionMaker {
    private final String login = "Bot" + UUID.randomUUID();
    private final String password = UUID.randomUUID().toString();
    private final Color color;
    private Stone[][] gameField = new Board().getField();
    private int enterCount = 0;

    public RandomBot(Color color) {
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
        if (enterCount == 0) {
            enterCount++;
            return new LoginPassword(RequestType.REGISTRATION, login, password);
        } else {
            return new LoginPassword(RequestType.LOGIN, login, password);
        }
    }


    @Override
    public GameConfig getGameConfig() throws ClientException {
        final int size = 9;
        if (color == Color.BLACK) {
            return new GameConfig(
                    RequestType.CREATE_GAME,
                    false,
                    color,
                    size,
                    UUID.randomUUID().toString());
        } else {
            return new GameConfig(
                    RequestType.JOIN_GAME,
                    false,
                    color,
                    size,
                    UUID.randomUUID().toString());
        }
    }

    @Override
    public Color getColor() throws ClientException {
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

    private GameAction skipTurn() {
        return new GameAction(RequestType.PASS, 0, 0);
    }

    public void setGameField(final Stone[][] gameField) {
        this.gameField = gameField;
    }
}
