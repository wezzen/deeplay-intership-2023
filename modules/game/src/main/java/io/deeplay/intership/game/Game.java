package io.deeplay.intership.game;

import io.deeplay.intership.exception.game.GameCode;
import io.deeplay.intership.exception.game.GameException;
import io.deeplay.intership.logger.GameLog;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Score;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.validation.Validation;

/**
 * Класс игры со всеми необходимыми для процесса игры полями.
 * Имеется валидатор, логгер, игровая доска, групповой контроль,
 * номер игры, проверщик конца игры, счётчик очков.
 */
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

    /**
     * Начинает игру, запускает логгер.
     * @return {@link Stone} массив изначального состояния доски
     */
    public Board startGame() {
        gameLog.startGame(gameId);
        return board;
    }

    /**
     * Реализует пропуск хода, через цвет понимаем, кто именно пропускает ход.
     * @param color цвет игрока, который пропускает ход
     * @return {@link Stone} возвращаем массив нового состояния доски
     */
    public Stone[][] skipTurn(Color color) {
        if (!checkGameOver.canSkipTurn()) {
            endGame();
        }
        gameLog.skipMove(color);
        return board.getField();
    }

    /**
     * Позволяет клиенту сделать ход в той позиции, в которой находится
     * поданный на вход Stone. Также здесь происходят групповые преобразования.
     * @param stone позиция, в которую делаем ход
     * @return {@link Stone} массив измененного состояния доски
     * @throws GameException при возникновении проблемы игрового процесса
     */
    public Stone[][] makeMove(Stone stone) throws GameException {
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
            gameLog.wrongMove(stone);
            throw new GameException(GameCode.PASSED);
        }

        return board.getField();
    }

    /**
     * Реализует окончание игры, с помощью флага gameIsOver в дальнейшем
     * проверяем при ходах, не закончилась ли игра.
     * Также подсчитываем очки, передавая доску классу ScoreCalculator.
     */
    public void endGame() {
        this.gameIsOver = true;
        ScoreCalculator scoreCalculator = new ScoreCalculator(board.getField());
        Score score = scoreCalculator.getTotalScore();
        gameLog.endGame(score.whitePoints() - score.blackPoints());
    }

    public boolean gameIsOver() {
        return gameIsOver;
    }

    /**
     * Реализует прибавление конкретному игроку заработанных очков.
     * @param points очки, которые нужно прибавить к имеющимся
     * @param color цвет игрока, который их заработал
     */
    private void addPoints(final int points, final Color color) {
        if (color == Color.BLACK) {
            scoreCalculator.addBlackPoints(points);
        } else {
            scoreCalculator.addWhitePoints(points);
        }
    }
}
