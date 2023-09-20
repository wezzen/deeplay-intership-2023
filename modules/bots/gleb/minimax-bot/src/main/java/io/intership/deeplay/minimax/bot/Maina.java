package io.intership.deeplay.minimax.bot;

import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Group;
import io.deeplay.intership.model.Stone;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Maina {

    private final int MAX_FIELD_RANGE = 0;
    private final int MIN_FIELD_RANGE = 8;

    private final Stone[][] gameField;

    public Maina(String s) {
        int n = s.length();
        gameField = new Stone[9][9];
        for(int i = 0; i < n; i++) {
            if(s.charAt(i) == 'B') {
                gameField[i / 9][i % 9] = new Stone(Color.BLACK, i/9, i%9, null);
            }
            else if(s.charAt(i) == 'W') {
                gameField[i / 9][i % 9] = new Stone(Color.WHITE, i/9, i%9, null);
            }
            else {
                gameField[i / 9][i % 9] = new Stone(Color.EMPTY, i/9, i%9, null);
            }
        }
    }

    public void makeGroups(Color color) {
        int n = gameField.length;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(gameField[i][j].getColor() == color && gameField[i][j].getGroup() == null) {
                    openGroup(i, j, color, new Group());
                }
            }
        }
    }

    public boolean ifIn(int i, int j) {
        return (i >= 0 && i <= 8 && j >= 0 && j <= 8);
    }

    public void openGroup(int i, int j, Color color, Group group) {
        gameField[i][j].setGroup(group);
        group.addStone(gameField[i][j]);
        if(i > MIN_FIELD_RANGE) {
            if(Color.EMPTY == gameField[i-1][j].getColor()) {
                group.addFreeCell(gameField[i-1][j]);
            }
            else if(color == gameField[i-1][j].getColor() && gameField[i-1][j].getGroup() == null) {
                openGroup(i-1, j, color, group);
            }
        }
        if(j > MIN_FIELD_RANGE) {
            if(Color.EMPTY == gameField[i][j-1].getColor()) {
                group.addFreeCell(gameField[i][j-1]);
            }
            else if(color == gameField[i][j-1].getColor() && gameField[i][j-1].getGroup() == null) {
                openGroup(i, j-1, color, group);
            }
        }
        if(i < MAX_FIELD_RANGE) {
            if(Color.EMPTY == gameField[i+1][j].getColor()) {
                group.addFreeCell(gameField[i+1][j]);
            }
            else if(color == gameField[i+1][j].getColor() && gameField[i+1][j].getGroup() == null) {
                openGroup(i+1, j, color, group);
            }
        }
        if(j < MAX_FIELD_RANGE) {
            if(Color.EMPTY == gameField[i][j+1].getColor()) {
                group.addFreeCell(gameField[i][j+1]);
            }
            else if(color == gameField[i][j+1].getColor() && gameField[i][j+1].getGroup() == null) {
                openGroup(i, j+1, color, group);
            }
        }

        if(ifIn(i-1, j-1)) {
            if(Color.EMPTY == gameField[i-1][j-1].getColor()) {
                group.addFreeCell(gameField[i-1][j-1]);
            }
            else if(color == gameField[i-1][j-1].getColor() && gameField[i-1][j-1].getGroup() == null) {
                openGroup(i-1, j-1, color, group);
            }
        }
        if(ifIn(i-1, j+1)) {
            if(Color.EMPTY == gameField[i-1][j+1].getColor()) {
                group.addFreeCell(gameField[i-1][j+1]);
            }
            else if(color == gameField[i-1][j+1].getColor() && gameField[i-1][j+1].getGroup() == null) {
                openGroup(i-1, j+1, color, group);
            }
        }
        if(ifIn(i+1, j-1)) {
            if(Color.EMPTY == gameField[i+1][j-1].getColor()) {
                group.addFreeCell(gameField[i+1][j-1]);
            }
            else if(color == gameField[i+1][j-1].getColor() && gameField[i+1][j-1].getGroup() == null) {
                openGroup(i+1, j-1, color, group);
            }
        }
        if(ifIn(i+1, j+1)) {
            if(Color.EMPTY == gameField[i+1][j+1].getColor()) {
                group.addFreeCell(gameField[i+1][j+1]);
            }
            else if(color == gameField[i+1][j+1].getColor() && gameField[i+1][j+1].getGroup() == null) {
                openGroup(i+1, j+1, color, group);
            }
        }
    }

    public Set<Group> getAllGroups() {
        int n = gameField.length;
        Set<Group> groups = new HashSet<>();
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(gameField[i][j].getColor() != Color.EMPTY)
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
            groups.addAll(getInnerGroups(stonesFromAreas, group));
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

    public void printSurrounded() {
        String arr[][] = new String[9][9];
        Set<Group> surroundedGroups = getSurroundedGroups(getAllGroups());
        for(Group group : surroundedGroups) {
            for(Stone stone : group.getStones()) {
                arr[stone.getRowNumber()][stone.getColumnNumber()] = "*";
            }
        }
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void print() {
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(gameField[i][j].getColor() == Color.BLACK){
                    System.out.print("B" + " ");
                }
                else if(gameField[i][j].getColor() == Color.WHITE){
                    System.out.print("W" + " ");
                }
                else {
                    System.out.print("E" + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printGroups() {
        int k = 0;
        Map<Group, Integer> sets = new HashMap();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Group group = gameField[i][j].getGroup();
                if (group == null) {
                    System.out.print("E" + " ");
                } else if (!sets.keySet().contains(group)) {
                    sets.put(group, k++);
                    System.out.print(k + " ");
                } else {
                    System.out.print(String.valueOf(sets.get(group)) + " ");
                }
            }
            System.out.println();
        }
    }

    public void printCoords (Group group){
        for(Stone stone : group.getStones()) {
            System.out.println(stone.getRowNumber() + " " + stone.getColumnNumber());
        }
    }

    public static void main(String[] args) {
        String s = "EEBBEEEEEEBEWBEEEBEBEWBEEBEEEBBEEBWWEEEEEEEBBWWEEEEEEBWBWWEEEBEEWBBWEBEWEEWWEEBWW";
        Maina maina = new Maina(s);
        maina.makeGroups(Color.WHITE);
        maina.makeGroups(Color.BLACK);
        System.out.println(maina.getAllGroups().size());
        for(Group group : maina.getAllGroups()) {
            maina.printCoords(group);
            break;
        }
        //maina.printGroups();
        //maina.print();
        //maina.printSurrounded();
        //maina.printGroups();
    }
}
