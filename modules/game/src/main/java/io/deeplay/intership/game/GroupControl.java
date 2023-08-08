package io.deeplay.intership.game;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class GroupControl {
    private final Logger logger = Logger.getLogger(GroupControl.class);
    private final Board board;
    private final int MAX_SIZE;

    public GroupControl(Board board) {
        this.board = board;
        MAX_SIZE = board.getField().length - 1;
    }

    public Set<Stone> getNearStonesByColor(Stone stone, Color color) {
        final int x = stone.getRowNumber();
        final int y = stone.getColumnNumber();
        Stone[][] field = board.getField();
        Set<Stone> nearStones = new HashSet<>();

        if (x > 0 && field[x - 1][y].getColor() == color) {
            nearStones.add(field[x - 1][y]);
        }
        if (x < MAX_SIZE && field[x + 1][y].getColor() == color) {
            nearStones.add(field[x + 1][y]);
        }
        if (y > 0 && field[x][y - 1].getColor() == color) {
            nearStones.add(field[x][y - 1]);
        }
        if (y < MAX_SIZE && field[x][y + 1].getColor() == color) {
            nearStones.add(field[x][y + 1]);
        }
        return nearStones;
    }

    public void removeGroup(Stone stone) {
        Set<Stone> enemyStones = getNearStonesByColor(stone, Color.invertColor(stone.getColor()));
        for (Stone enemyStone : enemyStones) {
            Group enemyGroup = enemyStone.getGroup();
            if (enemyGroup.getCountOfFreeDames() < 1) {
                enemyGroup.getStones().stream().forEach(new Consumer<Stone>() {
                    @Override
                    public void accept(Stone stone) {
                        stone.setGroup(new Group());
                        stone.setColor(Color.EMPTY);
                    }
                });
                board.removeGroup(enemyGroup);
            } else {
                enemyGroup.removeFreeCell(stone);
            }
        }
    }

    public void setGroup(Stone stone) {
        Set<Stone> friendStones = getNearStonesByColor(stone, stone.getColor());
        if (friendStones.isEmpty()) {
            logger.debug("не найдены смежные союзные камни");
            Group group = new Group(new HashSet<>(Arrays.asList(stone)),
                    getNearStonesByColor(stone, Color.EMPTY));
            stone.setGroup(group);
            board.addGroup(group);
        } else {
            Stone maxStone = friendStones.stream().max(new Comparator<Stone>() {
                @Override
                public int compare(Stone stone1, Stone stone2) {
                    int countOfStones1 = stone1.getGroup().getCountOfStones();
                    int countOfStones2 = stone2.getGroup().getCountOfStones();
                    return countOfStones1 - countOfStones2;
                }
            }).get();
            logger.debug("Найден смежный союзный камень с самой большой группой " + maxStone);

            maxStone.getGroup().addStone(stone);
            maxStone.getGroup().addFreeCells(getNearStonesByColor(stone, Color.EMPTY));
            stone.setGroup(maxStone.getGroup());

            for (Stone friendStone : friendStones) {
                if (friendStone.getGroup() != maxStone.getGroup()) {
                    Group friendGroup = friendStone.getGroup();
                    friendGroup.getStones().stream().forEach(new Consumer<Stone>() {
                        @Override
                        public void accept(Stone stone) {
                            stone.setGroup(maxStone.getGroup());
                        }
                    });
                    maxStone.getGroup().addStones(friendGroup.getStones());
                    maxStone.getGroup().addFreeCells(friendGroup.getFreeCells());
                    board.removeGroup(friendGroup);
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
        Stone[][] field = board.getField();
        Set<Stone> nearStones = new HashSet<>();
        if (x > 0) {
            nearStones.add(field[x - 1][y]);
        }
        if (x < MAX_SIZE) {
            nearStones.add(field[x + 1][y]);
        }
        if (y > 0) {
            nearStones.add(field[x][y - 1]);
        }
        if (y < MAX_SIZE) {
            nearStones.add(field[x][y + 1]);
        }
        return nearStones;
    }
}
