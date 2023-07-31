package io.deeplay.inership.server;

import java.util.Set;

public class Board {
  private Stone[][] field;
  private Set<Group> groups;

  public Board(Stone[][] field, Set<Group> groups) {
    this.field = field;
    this.groups = groups;
  }

  public Stone[][] getField() {
    return field;
  }

  public Set<Group> getGroups() {
    return groups;
  }
}
