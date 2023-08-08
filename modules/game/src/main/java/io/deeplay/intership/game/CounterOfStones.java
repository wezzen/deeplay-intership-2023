package io.deeplay.intership.game;

import java.util.ArrayList;
import java.util.List;

public class CounterOfStones {
    private String[][] field;
    private int counterOfGroups = 0;

    private int whitePoints = 0;

    private int blackPoints = 0;

    private List<Owner> owners;

    public CounterOfStones(Board board){
        this.field = createField(board);
        counterOfGroups = blackPoints = whitePoints = 0;
        owners = new ArrayList<>();
    }

    public String[][] createField(Board board){
        Stone[][] fieldBoard = board.getField();
        int sizeOfField = fieldBoard.length;
        String[][] field = new String[sizeOfField][sizeOfField];
        for(int i = 0; i < sizeOfField; i++){
            for(int j = 0; j < sizeOfField; j++){
                Color color = fieldBoard[i][j].getColor();
                if(color == Color.WHITE){
                    field[i][j] = "W";
                }
                else if(color == Color.BLACK){
                    field[i][j] = "B";
                }
                else{
                    field[i][j] = "E";
                }
            }
        }
        return field;
    }

    public void findGroupsOfEmptyStones(){
        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field.length; j++){
                if(field[i][j].equals("E")){
                    owners.add(Owner.NONE);
                    openGroup(i, j);
                    counterOfGroups++;
                }
            }
        }
    }

    public void openGroup(int i, int j){
        field[i][j] = String.valueOf(counterOfGroups);
        if(i > 0){
            if(field[i-1][j].equals("E")){
                openGroup(i-1, j);
            }
            else if(field[i-1][j].equals("W") || field[i-1][j].equals("B")){
                changeOwner(field[i-1][j]);
            }
        }
        if(i < field.length-1){
            if(field[i+1][j].equals("E")){
                openGroup(i+1, j);
            }
            else if(field[i+1][j].equals("W") || field[i+1][j].equals("B")){
                changeOwner(field[i+1][j]);
            }
        }
        if(j > 0){
            if(field[i][j-1].equals("E")){
                openGroup(i, j-1);
            }
            else if(field[i][j-1].equals("W") || field[i][j-1].equals("B")){
                changeOwner(field[i][j-1]);
            }
        }
        if(j < field.length-1){
            if(field[i][j+1].equals("E")){
                openGroup(i, j+1);
            }
            else if(field[i][j+1].equals("W") || field[i][j+1].equals("B")){
                changeOwner(field[i][j+1]);
            }
        }
    }

    public void changeOwner(String color){
        if(color.equals("B")){
            if(owners.get(counterOfGroups) == Owner.NONE){
                owners.set(counterOfGroups,Owner.BLACK);
            }
            else if(owners.get(counterOfGroups) == Owner.WHITE){
                owners.set(counterOfGroups,Owner.COMMON);
            }
        }
        else{
            if(owners.get(counterOfGroups) == Owner.NONE){
                owners.set(counterOfGroups,Owner.WHITE);
            }
            else if(owners.get(counterOfGroups) == Owner.BLACK){
                owners.set(counterOfGroups, Owner.COMMON);
            }
        }
    }

    public void countCapturedEmptyStones(){
        findGroupsOfEmptyStones();
        int sizeOfField = field.length;
        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                if(!field[i][j].equals("B") && !field[i][j].equals("W")){
                    if(owners.get(Integer.valueOf(field[i][j])) == Owner.BLACK){
                        blackPoints++;
                    }
                    else if(owners.get(Integer.valueOf(field[i][j])) == Owner.WHITE){
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


