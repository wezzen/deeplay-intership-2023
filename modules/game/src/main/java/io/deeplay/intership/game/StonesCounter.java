package io.deeplay.intership.game;

import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;

import java.util.ArrayList;
import java.util.List;

public class StonesCounter {
    private final String[][] field;
    private final int MIN_FIELD_RANGE;
    private final int MAX_FIELD_RANGE;
    private final List<Owner> owners;
    private int counterOfGroups;
    private int whitePoints;
    private int blackPoints;

    public StonesCounter(final Stone[][] gameField) {
        this.owners = new ArrayList<>();
        this.MIN_FIELD_RANGE = 0;
        this.MAX_FIELD_RANGE = gameField.length - 1;
        counterOfGroups = blackPoints = whitePoints = 0;
        this.field = createField(gameField);
    }

    /**
     * Создаем символьное представление нашего поля, на котором уже к этому
     * моменту расставлены все камни, игра окончена.
     * @param gameField
     * @return
     */
    private String[][] createField(final Stone[][] gameField) {
        final String[][] field = new String[MAX_FIELD_RANGE + 1][MAX_FIELD_RANGE + 1];
        for (int i = MIN_FIELD_RANGE; i <= MAX_FIELD_RANGE; i++) {
            for (int j = MIN_FIELD_RANGE; j <= MAX_FIELD_RANGE; j++) {
                field[i][j] = gameField[i][j].getColor().symbol;
            }
        }
        return field;
    }

    /**
     * Открываем группы пустых камней, стартуем из некоторого камня и постепенно
     * открываем всех его соседей, таким образом полуаются острова.
     */
    private void findGroupsOfEmptyStones() {
        for (int i = MIN_FIELD_RANGE; i <= MAX_FIELD_RANGE; i++) {
            for (int j = MIN_FIELD_RANGE; j <= MAX_FIELD_RANGE; j++) {
                if (field[i][j].equals(Color.EMPTY.symbol)) {
                    owners.add(Owner.NONE);
                    openGroup(i, j);
                    counterOfGroups++;
                }
            }
        }
    }

    /**
     * Здесь мы рекурсивно открываем каждую из групп, ходим по всем соседним
     * камням и объединяем их в одну группу.
     * @param i
     * @param j
     */
    private void openGroup(int i, int j) {
        field[i][j] = String.valueOf(counterOfGroups);
        if (i > MIN_FIELD_RANGE) {
            if (field[i - 1][j].equals(Color.EMPTY.symbol)) {
                openGroup(i - 1, j);
            } else if (field[i - 1][j].equals(Color.WHITE.symbol) || field[i - 1][j].equals(Color.BLACK.symbol)) {
                changeOwner(field[i - 1][j]);
            }
        }
        if (i < MAX_FIELD_RANGE) {
            if (field[i + 1][j].equals(Color.EMPTY.symbol)) {
                openGroup(i + 1, j);
            } else if (field[i + 1][j].equals(Color.WHITE.symbol) || field[i + 1][j].equals(Color.BLACK.symbol)) {
                changeOwner(field[i + 1][j]);
            }
        }
        if (j > MIN_FIELD_RANGE) {
            if (field[i][j - 1].equals(Color.EMPTY.symbol)) {
                openGroup(i, j - 1);
            } else if (field[i][j - 1].equals(Color.WHITE.symbol) || field[i][j - 1].equals(Color.BLACK.symbol)) {
                changeOwner(field[i][j - 1]);
            }
        }
        if (j < MAX_FIELD_RANGE) {
            if (field[i][j + 1].equals(Color.EMPTY.symbol)) {
                openGroup(i, j + 1);
            } else if (field[i][j + 1].equals(Color.WHITE.symbol) || field[i][j + 1].equals(Color.BLACK.symbol)) {
                changeOwner(field[i][j + 1]);
            }
        }
    }

    /**
     * Меняем владельца камней, если камни были ничьи, то назначаем хозяина
     * в зависимости от цвета непустого соседа. Если по соселству были как
     * белые, так и черные, значит они общие, в счет не пойдут никому.
     * @param color
     */
    private void changeOwner(final String color) {
        if (color.equals(Color.BLACK.symbol)) {
            if (owners.get(counterOfGroups) == Owner.NONE) {
                owners.set(counterOfGroups, Owner.BLACK);
            } else if (owners.get(counterOfGroups) == Owner.WHITE) {
                owners.set(counterOfGroups, Owner.COMMON);
            }
        } else {
            if (owners.get(counterOfGroups) == Owner.NONE) {
                owners.set(counterOfGroups, Owner.WHITE);
            } else if (owners.get(counterOfGroups) == Owner.BLACK) {
                owners.set(counterOfGroups, Owner.COMMON);
            }
        }
    }

    /**
     * По имеющемуся полю из камней, распределенных по группам (номерам)
     * считаем кому сколько досталось.
     */
    public void countCapturedEmptyStones() {
        findGroupsOfEmptyStones();
        for (int i = MIN_FIELD_RANGE; i <= MAX_FIELD_RANGE; i++) {
            for (int j = MIN_FIELD_RANGE; j <= MAX_FIELD_RANGE; j++) {
                if (!field[i][j].equals("B") && !field[i][j].equals("W")) {
                    incrementPoints(owners.get(Integer.parseInt(field[i][j])));
                    /*if (owners.get(Integer.parseInt(field[i][j])) == Owner.BLACK) {
                        blackPoints++;
                    } else if (owners.get(Integer.parseInt(field[i][j])) == Owner.WHITE) {
                        whitePoints++;
                    }*/
                }
            }
        }
    }

    /**
     * В зависимости от владельца окруженные камни идут в счет белому или
     * черному игроку.
     * @param owner
     */
    private void incrementPoints(final Owner owner) {
        if (Owner.BLACK == owner) {
            blackPoints++;
        }
        if (Owner.WHITE == owner) {
            whitePoints++;
        }
    }

    public int getWhitePoints() {
        return whitePoints;
    }

    public int getBlackPoints() {
        return blackPoints;
    }
}
