package io.deeplay.intership.game;

import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Group;
import io.deeplay.intership.model.Stone;
import jdk.jfr.Experimental;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Этот класс вычисляет захваченные пустые группы {@link Group} на поле игры Го.
 */
@Experimental
public class CapturedCellsCalculator {
    private final Stone[][] gameField;
    private final GroupControl groupControl;

    public CapturedCellsCalculator(Stone[][] gameField) {
        this.gameField = gameField;
        this.groupControl = new GroupControl(gameField);
    }

    /**
     * Получить набор пустых групп.
     *
     * @return Набор объектов {@link Group}, представляющих пустые группы цвета {@code Color} EMPTY.
     */
    public Set<Group> getCapturedEmptyGroups() {
        Set<Stone> emptyStones = findEmptyStones();
        for (Stone emptyStone : emptyStones) {
            groupControl.getNearStonesByColor(emptyStone, Color.EMPTY)
                    .forEach(stone -> mergeStonesGroup(stone, emptyStone));
        }
        return getUniqueGroups(emptyStones);
    }

    /**
     * Получить карту групп, окруженных указанной группой.
     *
     * @param group Группа {@link Group}, для которой необходимо определить, какие группы ее окружили.
     * @return {@link Map} объектов группы с количеством камней.
     */
    public Map<Group, Integer> getSurroundedGroups(final Group group) {
        Map<Group, Integer> surroundedGroups = new HashMap<>();
        for (Stone stone : group.getStones()) {
            if (surroundedGroups.containsKey(stone.getGroup())) {
                int newValue = surroundedGroups.get(stone.getGroup()) + 1;
                surroundedGroups.put(stone.getGroup(), newValue);
            } else {
                surroundedGroups.put(stone.getGroup(), 1);
            }
        }
        return surroundedGroups;
    }

    /**
     * Нужна для распределения пустых камней по группам для того, чтобы в конце
     * игры произвести подсчет захваченных камней.
     * @return {@link Set<Stone>} множество всех пустых камней на поле
     */
    private Set<Stone> findEmptyStones() {
        Set<Stone> emptyStones = new HashSet<>();
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                if (gameField[i][j].getColor() == Color.EMPTY) {
                    Group group = new Group();
                    group.addStone(gameField[i][j]);
                    gameField[i][j].setGroup(group);
                    emptyStones.add(gameField[i][j]);
                }
            }
        }
        return emptyStones;
    }

    /**
     * Нужна для проверки: разные ли группы оказались по соседству. Если разные,
     * то вызываем метод, который объединит их в одну группу.
     * @param firstStone {@link Stone}
     * @param secondStone {@link Stone}
     */
    private void mergeStonesGroup(final Stone firstStone, final Stone secondStone) {
        if (firstStone.getGroup() != secondStone.getGroup()) {
            mergeGroups(firstStone.getGroup(), secondStone.getGroup());
        }
    }

    /**
     * Производим слияние двух групп.
     * Помещаем камни из меньшей по размеру группы в большую.
     * @param firstGroup
     * @param secondGroup
     */
    private void mergeGroups(final Group firstGroup, final Group secondGroup) {
        if (firstGroup.getStonesCount() >= secondGroup.getStonesCount()) {
            firstGroup.getStones().addAll(secondGroup.getStones());

        } else {
            mergeGroups(secondGroup, firstGroup);
        }
    }

    /**
     * Из всего множества камней находим уникальные существующие группы камней,
     * чтобы дальше использовать эти группы при подсчете входящих в них камней.
     * @param stones {@link Set<Stone>}
     * @return
     */
    private Set<Group> getUniqueGroups(Set<Stone> stones) {
        Set<Group> groups = new HashSet<>();
        for (Stone item : stones) {
            groups.add(item.getGroup());
        }
        return groups;
    }
}
