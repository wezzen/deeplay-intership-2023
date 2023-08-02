package io.deeplay.intership.service;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
