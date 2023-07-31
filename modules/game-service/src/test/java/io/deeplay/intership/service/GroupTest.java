package io.deeplay.intership.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class GroupTest {

  @Test
  public void groupGettersTest() {

    Set<Stone> stones = new HashSet<>();
    Set<Stone> freeCells = new HashSet<>();
    Group group = new Group(stones, freeCells);

    assertNotNull(group.getStones());
    assertNotNull(group.getFreeCells());
  }
}
