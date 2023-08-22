package io.deeplay.intership.game;

import io.deeplay.intership.model.Stone;

public class ScoreCalculator {
    private static final double POINTS_COMPENSATION = 6.5;
    private final Stone[][] gameField;
    private int blackScore;
    private int whiteScore;

    public ScoreCalculator(Stone[][] gameField) {
        this.gameField = gameField;
        blackScore = 0;
        whiteScore = 0;
    }

    public void addBlackPoints(int pointsCount) {
        blackScore += pointsCount;
    }

    public void addWhitePoints(int pointsCount) {
        whiteScore += pointsCount;
    }

    public double getTotalScore() {
        //TODO: реализация подсчета полей
        return POINTS_COMPENSATION + blackScore + whiteScore;
    }
}
