package io.deeplay.intership.game;

import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Group;
import io.deeplay.intership.model.Stone;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Данный класс предназаначен для контроля формирования групп камней на поле,
 * он отвечает за удаление старых, формирование новых, а также слияние имеющихся групп.
 */
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

    /**
     * Берем все соседние камни определенного цвета, чтобы дальше использовать
     * при построении групп и проверке самоубийства.
     * @param stone {@link Stone} текущий камень
     * @param color {@link Color} цвет камней по соседству с текущим камнем
     * @return {@link Set<Stone>} множество соседей к текущему камню указанного цвета
     */
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

    /**
     * При очередном ходе проверяем, если новый камень забрал у какой-то
     * вражеской группы по соседству последнее дамэ, тогда удаляем эту группу.
     * @param stone {@link Stone} новый поставленный на поле камень
     * @return countOfRemovedStones количество удаленных с поля камней
     */
    public int removeGroup(Stone stone) {
        Set<Stone> enemyStones = getNearStonesByColor(stone, Color.invertColor(stone.getColor()));
        int countOfRemovedStones = 0;
        for (Stone enemyStone : enemyStones) {
            Group enemyGroup = enemyStone.getGroup();
            if (enemyGroup.getDamesCount() < 1) {
                countOfRemovedStones += enemyGroup.getStonesCount();
                enemyGroup.getStones().forEach((Stone eachStone) -> {
                    returnFreeCell(eachStone);
                    eachStone.setGroup(new Group());
                    eachStone.setColor(Color.EMPTY);
                });
            } else {
                enemyGroup.removeFreeCell(stone);
            }
        }
        return countOfRemovedStones;
    }

    //TODO: разбить метод на мелкие
    /**
     * Устанавливаем группу для новоиспеченной особи камня обыкновенного
     * если он может войти в несколько групп то сливаем эти группы в большую
     * по размеру, если группа по соседству одна, то просто в нее добавляем его.
     * Если групп нет по соседству, тогда создаем новую группу.
     * @param stone {@link Stone} камень, который нужно определить в группу
     */
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

    /**
     * Убираем этот камень из множества свободных дамэ соседних вражеских групп.
     * Используется, когда мы ставим на позицию новый камень.
     * @param stone {@link Stone} новый камень, который соседствует с вражескими
     */
    public void removeFreeCellFromNeighborStones(Stone stone) {
        Set<Stone> neighborStones = getNearStones(stone);
        for (var item : neighborStones) {
            if (item.getGroup() != null) {
                item.getGroup().removeFreeCell(stone);
            }
        }
    }

    /**
     * Берем все соседние к данному камни независимо от их цвета.
     * @param currentStone {@link Stone} некоторый камень, вокруг него смотрим
     * @return {@link Set<Stone>} множество всех соседних камней
     */
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

    /**
     * Возвращаем всем вражеским группам по соседстсву с данным камнем
     * этот камень в множество свободных дамэ, так как он был уничтожен.
     * У него либо у его группы не осталось свободных дамэ.
     * @param stone {@link Stone}  некоторый мертвый камень
     */
    private void returnFreeCell(Stone stone) {
        Color color = stone.getColor();
        Set<Stone> enemyStones = getNearStonesByColor(stone, Color.invertColor(color));
        for (Stone enemyStone : enemyStones) {
            enemyStone.getGroup().addFreeCell(stone);
        }
    }


    // NEW
    /*public boolean checkIfSurrounded (Group outGroup, Group inGroup) {
        Stone stone = (Stone)inGroup.getStones().stream().toArray()[0];
        int x = stone.getRowNumber();
        int y = stone.getColumnNumber();
        return ifClosed(stone, outGroup, -1, 0) &&
                ifClosed(stone, outGroup, 1, 0) &&
                ifClosed(stone, outGroup, 0, -1) &&
                ifClosed(stone, outGroup, 0, 1);
    }

    public boolean ifClosed(Stone stone, Group group, int i, int j) {
        int x = stone.getRowNumber();
        int y = stone.getColumnNumber();
        while(x >= 0 && x <= 8 && y <= 0 && y <= 8) {
            if(gameField[x][y].getGroup() == group) {
                return true;
            }
            x += i;
            y += j;
        }
        return false;
    }*/

    public Set<Group> getAllGroups() {
        int n = gameField.length;
        Set<Group> groups = new HashSet<>();
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                groups.add(gameField[i][j].getGroup());
            }
        }
        return groups;
    }

    public Set<Group> getSurroundedGroups(Set<Group> allGroups) {
        Set<Group> groups = new HashSet<>();
        for(Group group : allGroups) {
            Stone stone = (Stone)group.getStones().stream().toArray()[0];
            Set<Stone> stonesFromAreas = itSurrounds(group, stone.getColor());
        }
        return groups;
    }

    public Set<Group> getInnerGroups(Set<Stone> stonesFromAreas, Group group) {
        Set<Group> surroundedGroups = new HashSet<>();
        Color colorGroup = ((Stone)group.getStones().stream().toArray()[0]).getColor();
        for(Stone stone : stonesFromAreas) {
            Set<Group> tempGroups = new HashSet<>();
            Set<Stone> visited = new HashSet<>();
            searchGroups(tempGroups, stone, group, visited, colorGroup);
            surroundedGroups.addAll(tempGroups);
        }
        return surroundedGroups;
    }

    public void searchGroups(Set<Group> foundedGroups, Stone stone, Group group, Set<Stone> visited, Color colorGroup) {
        int i = stone.getRowNumber();
        int j = stone.getColumnNumber();
        visited.add(stone);
        if(stone.getColor() == Color.invertColor(colorGroup)) {
            foundedGroups.add(stone.getGroup());
        }

        if(i > MIN_FIELD_RANGE && gameField[i-1][j].getGroup() != group &&
                !visited.contains(gameField[i-1][j])) {
            searchGroups(foundedGroups, gameField[i-1][j], group, visited, colorGroup);
        }
        if(i < MAX_FIELD_RANGE && gameField[i+1][j].getGroup() != group &&
                !foundedGroups.contains(gameField[i+1][j].getGroup())) {
            searchGroups(foundedGroups, gameField[i+1][j], group, visited, colorGroup);
        }
        if(j > MIN_FIELD_RANGE && gameField[i][j-1].getGroup() != group &&
                !foundedGroups.contains(gameField[i][j-1].getGroup())) {
            searchGroups(foundedGroups, gameField[i][j-1], group, visited, colorGroup);
        }
        if(j < MAX_FIELD_RANGE && gameField[i][j+1].getGroup() != group &&
                !foundedGroups.contains(gameField[i][j+1].getGroup())) {
            searchGroups(foundedGroups, gameField[i][j+1], group, visited, colorGroup);
        }
    }

    public Set<Stone> itSurrounds(Group group, Color color) {
        int n = gameField.length;
        Set<Group> groups = new HashSet<>();
        Stone[][] tempField = new Stone[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(gameField[i][j].getGroup() == group) {
                    tempField[i][j] = new Stone(color, i, j, group);
                }
                else {
                    tempField[i][j] = new Stone(Color.EMPTY, i, j);
                }
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                Stone stone = tempField[i][j];
                if(stone.getGroup() != group && !groups.contains(stone.getGroup())) {
                    Group newGroup = new Group();
                    groups.add(newGroup);
                    open(tempField, i, j, newGroup);
                }
            }
        }

        Set<Stone> areas = new HashSet<>();

        Group maxGroup = groups
                .stream()
                .max((group1, group2) -> {
                    int countOfStones1 = group1.getStonesCount();
                    int countOfStones2 = group2.getStonesCount();
                    return countOfStones1 - countOfStones2;
                })
                .get();
        groups.remove(maxGroup);
        for(Group surroundedGroup : groups) {
            areas.add((Stone)surroundedGroup.getStones().stream().toArray()[0]);
        }
        return areas;
    }

    public void open(Stone[][] tempField, int i, int j, Group group) {
        group.addStone(tempField[i][j]);
        tempField[i][j].setGroup(group);
        Color curColor = tempField[i][j].getColor();
        Group curGroup = tempField[i][j].getGroup();
        if(i > 0 && curGroup != group && curColor == Color.EMPTY) {
            open(tempField, i-1, j, group);
        }
        if(j > 0 && curGroup != group && curColor == Color.EMPTY) {
            open(tempField, i-1, j, group);
        }
        if(i > 0 && curGroup != group && curColor == Color.EMPTY) {
            open(tempField, i-1, j, group);
        }
        if(i > 0 && curGroup != group && curColor == Color.EMPTY) {
            open(tempField, i-1, j, group);
        }
    }
}
