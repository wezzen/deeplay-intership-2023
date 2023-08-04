package io.deeplay.intership.service;

import java.util.*;
import java.util.function.Consumer;

public class GroupControl {
    public final Board board;
    public GroupControl(Board board){
        this.board = board;
    }

    Set<Stone> getNearStones(Stone stone, Color color){
        int x = stone.getX();
        int y = stone.getY();
        Stone[][] field = board.getField();
        Set<Stone> nearStones = new HashSet<>();
        if(x > 0 && field[x-1][y].getColor() == color){
            nearStones.add(field[x-1][y]);
        }
        if(x < board.getField().length && field[x+1][y].getColor() == color){
            nearStones.add(field[x+1][y]);
        }
        if(y > 0 && field[x][y-1].getColor() == color){
            nearStones.add(field[x][y-1]);
        }
        if(y < board.getField().length && field[x][y+1].getColor() == color){
            nearStones.add(field[x][y+1]);
        }
        return nearStones;
    }

    void removeGroup(Stone stone){
        Color stoneColor = stone.getColor();
        Color enemyColor = Color.values()[(stoneColor.ordinal() + 1)%2];
        Set<Stone> enemyStones = getNearStones(stone, enemyColor);
        for(Stone enemyStone : enemyStones){
            Group enemyGroup = enemyStone.getGroup();
            if(enemyGroup.getCountOfFreeDames() < 2){
                enemyGroup.getStones().stream().forEach(new Consumer<Stone>() {
                    @Override
                    public void accept(Stone stone) {
                        stone.setGroup(new Group());
                        stone.setColor(Color.EMPTY);
                    }
                });
                board.removeGroup(enemyGroup);
            }
        }
    }

    public void setGroup(Stone stone){
        Set<Stone> friendStones = getNearStones(stone, stone.getColor());
        if(friendStones.isEmpty()){
            Group group = new Group(new HashSet<>(Arrays.asList(stone)),
                                    getNearStones(stone, Color.EMPTY));
            stone.setGroup(group);
            board.addGroup(group);
        }
        else{
            Stone maxStone = friendStones.stream().max(new Comparator<Stone>() {
                @Override
                public int compare(Stone stone1, Stone stone2) {
                    int countOfStones1 = stone1.getGroup().getCountOfStones();
                    int countOfStones2 = stone2.getGroup().getCountOfStones();
                    return countOfStones1 - countOfStones2;
                }
            }).get();

            maxStone.getGroup().addStone(stone);
            maxStone.getGroup().addFreeCells(getNearStones(stone, Color.EMPTY));
            stone.setGroup(maxStone.getGroup());

            for(Stone friendStone : friendStones){
                if(friendStone.getGroup() != maxStone.getGroup()){
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
}
