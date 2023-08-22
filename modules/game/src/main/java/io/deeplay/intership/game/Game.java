package io.deeplay.intership.game;

import io.deeplay.intership.logger.GameLog;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Score;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.validation.Validation;

public class Game {
    private static int idGenerator = 1;
    private final int gameId;
    private final Board board;
    private final GameLog gameLog;
    private final CheckGameOver checkGameOver;
    private final GroupControl groupControl;
    private final Validation validation;
    private final ScoreCalculator scoreCalculator;
    private boolean gameIsOver;

    public Game() {
        this.gameId = idGenerator++;
        this.board = new Board();
        this.gameLog = new GameLog();
        this.checkGameOver = new CheckGameOver();
        this.groupControl = new GroupControl(board.getField());
        this.validation = new Validation(board);
        this.scoreCalculator = new ScoreCalculator(board.getField());
        this.gameIsOver = false;
    }

    public Board startGame() {
        gameLog.startGame(gameId);
        return board;
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
            board.getField()[stone.getRowNumber()][stone.getColumnNumber()].setColor(stone.getColor());
            stone = board.getField()[stone.getRowNumber()][stone.getColumnNumber()];
            checkGameOver.resetSkipCount();
            board.updateLastMoveState(stone);
            groupControl.setGroup(stone);
            groupControl.removeFreeCellFromNeighborStones(stone);
            int removedStonesCount = groupControl.removeGroup(stone);
            addPoints(removedStonesCount, stone.getColor());

            gameLog.move(stone);
        } else {
            gameLog.wrongMove(stone.getColor());
        }

        return board;
    }

    public void endGame() {
        ScoreCalculator scoreCalculator = new ScoreCalculator(board.getField());
        Score score = scoreCalculator.getTotalScore();
        gameLog.endGame(score.whitePoints() - score.blackPoints());
        this.gameIsOver = true;
    }

    public boolean gameIsOver() {
        return gameIsOver;
    }

    private void addPoints(final int points, final Color color) {
        if (color == Color.BLACK) {
            scoreCalculator.addBlackPoints(points);
        } else {
            scoreCalculator.addWhitePoints(points);
        }
    }
}
