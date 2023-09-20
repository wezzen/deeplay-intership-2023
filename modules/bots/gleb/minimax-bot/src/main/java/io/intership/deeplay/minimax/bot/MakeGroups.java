package io.intership.deeplay.minimax.bot;

import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Group;
import io.deeplay.intership.model.Stone;

public class MakeGroups extends Thread {
    private Stone[][] field;
    private Color color;

    @Override
    public void run() {
        makeGroups(this.field, this.color);
    }

    public void setProperties(Stone[][] field, Color color) {
        this.field = field;
        this.color = color;
    }

    public void makeGroups(Stone[][] field, Color color) {
        int n = field.length;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(field[i][j].getColor() == color && field[i][j].getGroup() == null) {
                    open(field, i, j, color, new Group());
                }
            }
        }
    }

    public void open(Stone[][] field, int i, int j, Color color, Group group) {
        field[i][j].setGroup(group);
        group.addStone(field[i][j]);
        if(i > 0) {
            if(Color.EMPTY == field[i-1][j].getColor()) {
                group.addFreeCell(field[i-1][j]);
            }
            else if(color == field[i-1][j].getColor() && field[i-1][j].getGroup() == null) {
                open(field, i - 1, j, color, group);
            }
        }
        if(j > 0) {
            if(Color.EMPTY == field[i][j-1].getColor()) {
                group.addFreeCell(field[i][j-1]);
            }
            else if(color == field[i][j-1].getColor() && field[i][j-1].getGroup() == null) {
                open(field, i, j - 1, color, group);
            }
        }
        if(i < 8) {
            if(Color.EMPTY == field[i+1][j].getColor()) {
                group.addFreeCell(field[i+1][j]);
            }
            else if(color == field[i+1][j].getColor() && field[i+1][j].getGroup() == null) {
                open(field, i + 1, j, color, group);
            }
        }
        if(j < 8) {
            if(Color.EMPTY == field[i][j+1].getColor()) {
                group.addFreeCell(field[i][j+1]);
            }
            else if(color == field[i][j+1].getColor() && field[i][j+1].getGroup() == null) {
                open(field, i, j + 1, color, group);
            }
        }
    }
}
