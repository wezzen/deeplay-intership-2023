package io.deeplay.intership.game;

import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Group;
import io.deeplay.intership.model.Score;
import io.deeplay.intership.model.Stone;

import java.util.Map;
import java.util.Set;

public class ScoreCalculator {
    private static final double POINTS_COMPENSATION = 6.5;
    private final StonesCounter stonesCounter;
    private final CapturedCellsCalculator cellsCalculator;
    private int blackScore;
    private int whiteScore;

    public ScoreCalculator(Stone[][] gameField) {
        this.stonesCounter = new StonesCounter(gameField);
        this.cellsCalculator = new CapturedCellsCalculator(gameField);
        blackScore = 0;
        whiteScore = 0;
    }

    /**
     * Используем при подсчете захваченных камней, соответственно прибавляем
     * белые камни, окруженные черными в счет черных.
     * @param pointsCount количество новых полученных черным игроком очков
     */
    public void addBlackPoints(int pointsCount) {
        blackScore += pointsCount;
    }

    /**
     * Используем при подсчете захваченных камней, соответственно прибавляем
     * черные камни, окруженные белыми в счет белых.
     * @param pointsCount количество новых полученных белым игроком очков
     */
    public void addWhitePoints(int pointsCount) {
        whiteScore += pointsCount;
    }

    /**
     * Возвращаем общий счет для вывода на экран в конце игры, чтобы понять,
     * кто победил.
     * @return {@link Score} возвращет финальный счет игры
     */
    public Score getTotalScore() {
        stonesCounter.countCapturedEmptyStones();
        blackScore += stonesCounter.getBlackPoints();
        whiteScore += stonesCounter.getWhitePoints();

        return new Score(
                blackScore,
                whiteScore + POINTS_COMPENSATION);
    }
}
