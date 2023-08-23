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

    public void addBlackPoints(int pointsCount) {
        blackScore += pointsCount;
    }

    public void addWhitePoints(int pointsCount) {
        whiteScore += pointsCount;
    }

    public Score getTotalScore() {
        stonesCounter.countCapturedEmptyStones();
        blackScore += stonesCounter.getBlackPoints();
        whiteScore += stonesCounter.getWhitePoints();

        return new Score(
                blackScore,
                whiteScore + POINTS_COMPENSATION);
    }
}
