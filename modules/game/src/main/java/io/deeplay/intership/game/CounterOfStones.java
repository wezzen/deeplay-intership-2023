package io.deeplay.intership.game;

import java.util.*;
import java.util.function.Consumer;

public class CounterOfStones {
    private Board board;
    private String[][] field;
    private final int MIN_FIELD_RANGE;
    private final int MAX_FIELD_RANGE;
    private int counterOfGroups;
    private int whitePoints;
    private int blackPoints;
    private List<Owner> owners;

    public CounterOfStones(Board board) {
        this.owners = new ArrayList<>();
        this.MIN_FIELD_RANGE = 0;
        this.MAX_FIELD_RANGE = board.getField().length-1;
        counterOfGroups = blackPoints = whitePoints = 0;
        this.field = createField(board);
    }

    public String[][] createField(Board board) {
        Stone[][] fieldBoard = board.getField();
        String[][] field = new String[MAX_FIELD_RANGE +1][MAX_FIELD_RANGE +1];
        for(int i = MIN_FIELD_RANGE; i <= MAX_FIELD_RANGE; i++){
            for(int j = MIN_FIELD_RANGE; j <= MAX_FIELD_RANGE; j++){
                Color color = fieldBoard[i][j].getColor();
                if (color == Color.WHITE) {
                    field[i][j] = "W";
                } else if (color == Color.BLACK) {
                    field[i][j] = "B";
                } else {
                    field[i][j] = "E";
                }
            }
        }
        return field;
    }

    public void findGroupsOfEmptyStones(){
        for(int i = MIN_FIELD_RANGE; i <= MAX_FIELD_RANGE; i++){
            for(int j = MIN_FIELD_RANGE; j <= MAX_FIELD_RANGE; j++){
                if(field[i][j].equals("E")){
                    owners.add(Owner.NONE);
                    openGroup(i, j);
                    counterOfGroups++;
                }
            }
        }
    }

    public void openGroup(int i, int j) {
        field[i][j] = String.valueOf(counterOfGroups);
        if(i > MIN_FIELD_RANGE){
            if(field[i-1][j].equals("E")){
                openGroup(i-1, j);
            }
            else if(field[i-1][j].equals("W") || field[i-1][j].equals("B")){
                changeOwner(field[i-1][j]);
            }
        }
        if(i < MAX_FIELD_RANGE){
            if(field[i+1][j].equals("E")){
                openGroup(i+1, j);
            }
            else if(field[i+1][j].equals("W") || field[i+1][j].equals("B")){
                changeOwner(field[i+1][j]);
            }
        }
        if(j > MIN_FIELD_RANGE){
            if(field[i][j-1].equals("E")){
                openGroup(i, j-1);
            }
            else if(field[i][j-1].equals("W") || field[i][j-1].equals("B")){
                changeOwner(field[i][j-1]);
            }
        }
        if(j < MAX_FIELD_RANGE){
            if(field[i][j+1].equals("E")){
                openGroup(i, j+1);
            }
            else if(field[i][j+1].equals("W") || field[i][j+1].equals("B")){
                changeOwner(field[i][j+1]);
            }
        }
    }

    public void changeOwner(String color) {
        if (color.equals("B")) {
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
        for (int i = MIN_FIELD_RANGE; i <= MAX_FIELD_RANGE; i++) {
            for (int j = MIN_FIELD_RANGE; j <= MAX_FIELD_RANGE; j++) {
                if(!field[i][j].equals("B") && !field[i][j].equals("W")){
                    if(owners.get(Integer.valueOf(field[i][j])) == Owner.BLACK){
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


