package io.deeplay.intership.game;

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
            skipTurn();
        } else {
            makeMove(stone);
        }
    }

    public void skipTurn() {
        //TODO: Skip для логгера
        if (!checkGameOver.canSkipTurn()) {
            endGame();
        }
    }

    public Board makeMove(Stone stone) {
        if (!checkGameOver.canMakeMove(stone.getColor())) {
            //если у игрока не осталось камней, то автоматически засчитывается пропуск хода
            skipTurn();
        }

        if (validation.isCorrectMove(stone.getColor(), stone.getColumnNumber(), stone.getRowNumber())) {
            checkGameOver.resetSkipCount();
            gameField.getField()[stone.getRowNumber()][stone.getColumnNumber()].setColor(stone.getColor());
            gameLog.move(stone);
        } else {
            gameLog.wrongMove(Color.WHITE);
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
