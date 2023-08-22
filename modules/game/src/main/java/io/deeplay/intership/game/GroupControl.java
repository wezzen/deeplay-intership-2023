package io.deeplay.intership.game;

import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Group;
import io.deeplay.intership.model.Stone;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupControl {
    private final Logger logger = Logger.getLogger(GroupControl.class);
    private final Stone[][] gameField;
    private final int MAX_FIELD_RANGE;
    private final int MIN_FIELD_RANGE;

    public GroupControl(Stone[][] gameField) {
        this.gameField = gameField;
        this.MAX_FIELD_RANGE = gameField.length - 1;
        this.MIN_FIELD_RANGE = 0;
    }

    public Set<Stone> getNearStonesByColor(Stone stone, Color color) {
        final int x = stone.getRowNumber();
        final int y = stone.getColumnNumber();
        Set<Stone> nearStones = new HashSet<>();

        if (x > MIN_FIELD_RANGE && gameField[x - 1][y].getColor() == color) {
            nearStones.add(gameField[x - 1][y]);
        }
        if (x < MAX_FIELD_RANGE && gameField[x + 1][y].getColor() == color) {
            nearStones.add(gameField[x + 1][y]);
        }
        if (y > MIN_FIELD_RANGE && gameField[x][y - 1].getColor() == color) {
            nearStones.add(gameField[x][y - 1]);
        }
        if (y < MAX_FIELD_RANGE && gameField[x][y + 1].getColor() == color) {
            nearStones.add(gameField[x][y + 1]);
        }
        return nearStones;
    }

    public int removeGroup(Stone stone) {
        Set<Stone> enemyStones = getNearStonesByColor(stone, Color.invertColor(stone.getColor()));
        int countOfRemovedStones = 0;
        for (Stone enemyStone : enemyStones) {
            Group enemyGroup = enemyStone.getGroup();
            if (enemyGroup.getFreeDamesCount() < 1) {
                countOfRemovedStones += enemyGroup.getStonesCount();
                enemyGroup.getStones().forEach((Stone eachStone) -> {
                    returnFreeCell(eachStone);
                    eachStone.setGroup(null);
                    eachStone.setColor(Color.EMPTY);
                });
            } else {
                enemyGroup.removeFreeCell(stone);
            }
        }
        return countOfRemovedStones;
    }

    //TODO: разбить метод на мелкие
    public void setGroup(Stone stone) {
        Set<Stone> friendStones = getNearStonesByColor(stone, stone.getColor());
        if (friendStones.isEmpty()) {
            logger.debug("не найдены смежные союзные камни");
            Group group = new Group(new HashSet<>(List.of(stone)),
                    getNearStonesByColor(stone, Color.EMPTY));
            stone.setGroup(group);
        } else {
            Stone maxStone = friendStones
                    .stream()
                    .max((stone1, stone2) -> {
                        int countOfStones1 = stone1.getGroup().getStonesCount();
                        int countOfStones2 = stone2.getGroup().getStonesCount();
                        return countOfStones1 - countOfStones2;
                    })
                    .get();
            logger.debug("Найден смежный союзный камень с самой большой группой " + maxStone);

            maxStone.getGroup().addStone(stone);
            maxStone.getGroup().addFreeCells(getNearStonesByColor(stone, Color.EMPTY));
            stone.setGroup(maxStone.getGroup());

            for (Stone friendStone : friendStones) {
                if (friendStone.getGroup() != maxStone.getGroup()) {
                    Group friendGroup = friendStone.getGroup();

                    friendGroup.getStones().forEach(item -> item.setGroup(maxStone.getGroup()));
                    maxStone.getGroup().addStones(friendGroup.getStones());
                    maxStone.getGroup().addFreeCells(friendGroup.getFreeCells());
                }
            }
            maxStone.getGroup().removeFreeCell(stone);
        }
    }

    public void removeFreeCellFromNeighborStones(Stone stone) {
        Set<Stone> neighborStones = getNearStones(stone);
        for (var item : neighborStones) {
            if (item.getGroup() != null) {
                item.getGroup().removeFreeCell(stone);
            }
        }
    }

    public Set<Stone> getNearStones(Stone currentStone) {
        int x = currentStone.getRowNumber();
        int y = currentStone.getColumnNumber();
        Set<Stone> nearStones = new HashSet<>();
        if (x > MIN_FIELD_RANGE) {
            nearStones.add(gameField[x - 1][y]);
        }
        if (x < MAX_FIELD_RANGE) {
            nearStones.add(gameField[x + 1][y]);
        }
        if (y > MIN_FIELD_RANGE) {
            nearStones.add(gameField[x][y - 1]);
        }
        if (y < MAX_FIELD_RANGE) {
            nearStones.add(gameField[x][y + 1]);
        }
        return nearStones;
    }

    private void returnFreeCell(Stone stone) {
        Color color = stone.getColor();
        Set<Stone> enemyStones = getNearStonesByColor(stone, Color.invertColor(color));
        for (Stone enemyStone : enemyStones) {
            enemyStone.getGroup().addFreeCell(stone);
        }
    }

}
