package io.deeplay.intership.service;

import java.util.HashSet;
import java.util.Set;

public class Group {
  private Set<Stone> stones;
  private Set<Stone> freeCells;
  public Group(Set<Stone> stones, Set<Stone> freeCells) {
    this.stones = stones;
    this.freeCells = freeCells;
  }
  public Group(){
    stones = new HashSet<>();
    freeCells = new HashSet<>();
  }
  public Set<Stone> getStones() {
    return stones;
  }
  public Set<Stone> getFreeCells() {
    return freeCells;
  }
  public void addStone(Stone stone){
    stones.add(stone);
  }
  public void addFreeCell(Stone stone){
    freeCells.add(stone);
  }
  public int getCountOfFreeDames(){
    return freeCells.size();
  }
}
