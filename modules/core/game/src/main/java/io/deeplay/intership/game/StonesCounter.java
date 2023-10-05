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
     * @param gameField {@link Stone} изначальный массив поля
     * @return {@link String} массив символьного представления поля
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
     * @param row номер строки нашего поля
     * @param column номер столбца нашего поля
     */
    private void openGroup(int row, int column) {
        field[row][column] = String.valueOf(counterOfGroups);
        if (row > MIN_FIELD_RANGE) {
            if (field[row - 1][column].equals(Color.EMPTY.symbol)) {
                openGroup(row - 1, column);
            } else if (field[row - 1][column].equals(Color.WHITE.symbol) || field[row - 1][column].equals(Color.BLACK.symbol)) {
                changeOwner(field[row - 1][column]);
            }
        }
        if (row < MAX_FIELD_RANGE) {
            if (field[row + 1][column].equals(Color.EMPTY.symbol)) {
                openGroup(row + 1, column);
            } else if (field[row + 1][column].equals(Color.WHITE.symbol) || field[row + 1][column].equals(Color.BLACK.symbol)) {
                changeOwner(field[row + 1][column]);
            }
        }
        if (column > MIN_FIELD_RANGE) {
            if (field[row][column - 1].equals(Color.EMPTY.symbol)) {
                openGroup(row, column - 1);
            } else if (field[row][column - 1].equals(Color.WHITE.symbol) || field[row][column - 1].equals(Color.BLACK.symbol)) {
                changeOwner(field[row][column - 1]);
            }
        }
        if (column < MAX_FIELD_RANGE) {
            if (field[row][column + 1].equals(Color.EMPTY.symbol)) {
                openGroup(row, column + 1);
            } else if (field[row][column + 1].equals(Color.WHITE.symbol) || field[row][column + 1].equals(Color.BLACK.symbol)) {
                changeOwner(field[row][column + 1]);
            }
        }
    }

    /**
     * Меняем владельца камней, если камни были ничьи, то назначаем хозяина
     * в зависимости от цвета непустого соседа. Если по соселству были как
     * белые, так и черные, значит они общие, в счет не пойдут никому.
     * @param color цвет текущего владельца некоторой группы камней
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
     * @param owner владелец некоторой группы камней
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
