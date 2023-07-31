package io.deeplay.intership.service;

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

  public Set<Stone> getFreeCells() {
    return freeCells;
  }
}
