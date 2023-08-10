package io.deeplay.intership.game;

import java.util.ArrayList;
import java.util.List;

public class CounterOfStones {
    private String[][] field;
    private final int minRangeOfField;
    private final int maxRangeOfField;
    private final String WHITE = "W";
    private final String BLACK = "B";
    private final String EMPTY = "E";
    private int counterOfGroups = 0;
    private int whitePoints = 0;
    private int blackPoints = 0;
    private List<Owner> owners;

    public CounterOfStones(Board board) {
        this.field = createField(board);
        counterOfGroups = blackPoints = whitePoints = 0;
        owners = new ArrayList<>();
        minRangeOfField = 0;
        maxRangeOfField = field.length;
    }

    public String[][] createField(Board board) {
        Stone[][] fieldBoard = board.getField();
        int sizeOfField = fieldBoard.length;
        String[][] field = new String[sizeOfField][sizeOfField];
        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                Color color = fieldBoard[i][j].getColor();
                if (color == Color.WHITE) {
                    field[i][j] = WHITE;
                } else if (color == Color.BLACK) {
                    field[i][j] = BLACK;
                } else {
                    field[i][j] = EMPTY;
                }
            }
        }
        return field;
    }

    public void findGroupsOfEmptyStones() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j].equals(WHITE)) {
                    owners.add(Owner.NONE);
                    openGroup(i, j);
                    counterOfGroups++;
                }
            }
        }
    }

    public void openGroup(int i, int j) {
        field[i][j] = String.valueOf(counterOfGroups);
        if (i > minRangeOfField) {
            if (field[i - 1][j].equals(EMPTY)) {
                openGroup(i - 1, j);
            } else if (field[i - 1][j].equals(WHITE) || field[i - 1][j].equals(BLACK)) {
                changeOwner(field[i - 1][j]);
            }
        }
        if (i < maxRangeOfField - 1) {
            if (field[i + 1][j].equals(EMPTY)) {
                openGroup(i + 1, j);
            } else if (field[i + 1][j].equals(WHITE) || field[i + 1][j].equals(BLACK)) {
                changeOwner(field[i + 1][j]);
            }
        }
        if (j > minRangeOfField) {
            if (field[i][j - 1].equals(EMPTY)) {
                openGroup(i, j - 1);
            } else if (field[i][j - 1].equals(WHITE) || field[i][j - 1].equals(BLACK)) {
                changeOwner(field[i][j - 1]);
            }
        }
        if (j < maxRangeOfField - 1) {
            if (field[i][j + 1].equals(EMPTY)) {
                openGroup(i, j + 1);
            } else if (field[i][j + 1].equals(WHITE) || field[i][j + 1].equals(BLACK)) {
                changeOwner(field[i][j + 1]);
            }
        }
    }

    public void changeOwner(String color) {
        if (color.equals(BLACK)) {
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

    public void countCapturedEmptyStones() {
        findGroupsOfEmptyStones();
        int sizeOfField = field.length;
        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                if (!field[i][j].equals(BLACK) && !field[i][j].equals(WHITE)) {
                    if (owners.get(Integer.valueOf(field[i][j])) == Owner.BLACK) {
                        blackPoints++;
                    } else if (owners.get(Integer.valueOf(field[i][j])) == Owner.WHITE) {
                        whitePoints++;
                    }
                }
            }
        }
    }

    public int getWhitePoints() {
        return whitePoints;
    }

    public int getBlackPoints() {
        return blackPoints;
    }
}


