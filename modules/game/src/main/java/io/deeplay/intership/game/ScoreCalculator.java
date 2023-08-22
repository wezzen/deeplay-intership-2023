package io.deeplay.intership.game;

import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Group;
import io.deeplay.intership.model.Score;
import io.deeplay.intership.model.Stone;

import java.util.Map;
import java.util.Set;

public class ScoreCalculator {
    private static final double POINTS_COMPENSATION = 6.5;
    private final CapturedCellsCalculator cellsCalculator;
    private int blackScore;
    private int whiteScore;

    public ScoreCalculator(Stone[][] gameField) {
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
        Set<Group> capturedEmptyGroups = cellsCalculator.getCapturedEmptyGroups();
        for (var item : capturedEmptyGroups) {
            Map<Group, Integer> info = cellsCalculator.getSurroundedGroups(item);
            Group maxGroup = new Group();
            for (var elem : info.entrySet()) {
                if (maxGroup.getStonesCount() < elem.getValue()) {
                    maxGroup = elem.getKey();
                }
            }
            if (Color.BLACK == maxGroup.getStones().iterator().next().getColor()) {
                blackScore += capturedEmptyGroups.size();
            } else {
                whiteScore += capturedEmptyGroups.size();
            }
        }
        return new Score(
                blackScore,
                whiteScore + POINTS_COMPENSATION);
    }
}
