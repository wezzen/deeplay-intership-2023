package io.deeplay.inership.server;

import java.util.Set;

public class Group {
  private Set<Stone> stones;
  private Set<Stone> freeCells;

  public Group(Set<Stone> stones, Set<Stone> freeCells) {
    this.stones = stones;
    this.freeCells = freeCells;
  }

  public Set<Stone> getStones() {
    return stones;
  }

  public void setStones(Set<Stone> stones) {
    this.stones = stones;
  }

  public Set<Stone> getFreeCells() {
    return freeCells;
  }

  public void setFreeCells(Set<Stone> freeCells) {
    this.freeCells = freeCells;
  }
}
