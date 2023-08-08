package io.deeplay.intership.game;

import org.apache.log4j.Logger;

public class Game {
    private static int idGenerator = 1;
    private final int gameId;
    private final Board gameField;
    private final GameLog gameLog;
    private final CheckGameOver checkGameOver;
    private final GroupControl groupControl;
    private final Validation validation;
    private boolean gameIsOver;

    public Game() {
        this.gameId = idGenerator++;
        this.gameField = new Board();
        this.gameLog = new GameLog();
        this.checkGameOver = new CheckGameOver();
        this.groupControl = new GroupControl(gameField);
        this.validation = new Validation(gameField);
        this.gameIsOver = false;
    }

    public Board startGame() {
        gameLog.startGame(gameId);
        return gameField;
    }

    public void analyzeMove(Stone stone) {
        if (stone.getColor() == Color.EMPTY) {
            skipTurn(stone.getColor());
        } else {
            makeMove(stone);
        }
    }

    public void skipTurn(Color color) {
        if (!checkGameOver.canSkipTurn()) {
            endGame();
        }
        gameLog.skipMove(color);
    }

    public Board makeMove(Stone stone) {
        if (!checkGameOver.canMakeMove(stone.getColor())) {
            //если у игрока не осталось камней, то автоматически засчитывается пропуск хода
            skipTurn(stone.getColor());
        }

        if (validation.isCorrectMove(stone.getColor(), stone.getRowNumber(), stone.getColumnNumber())) {
            gameField.getField()[stone.getRowNumber()][stone.getColumnNumber()].setColor(stone.getColor());
            stone = gameField.getField()[stone.getRowNumber()][stone.getColumnNumber()];
            checkGameOver.resetSkipCount();
            gameField.updateLastMoveState(stone);
            groupControl.setGroup(stone);
            groupControl.removeFreeCellFromNeighborStones(stone);
            groupControl.removeGroup(stone);

            gameLog.move(stone);
        } else {
            gameLog.wrongMove(stone.getColor());
        }

        return gameField;
    }

    public void endGame() {
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        gameLog.endGame(scoreCalculator.getTotalScore());
        this.gameIsOver = true;
    }

    public boolean gameIsOver() {
        return gameIsOver;
    }
}
