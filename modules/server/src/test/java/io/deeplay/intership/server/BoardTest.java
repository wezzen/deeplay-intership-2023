package io.deeplay.intership.server;

import io.deeplay.inership.server.Board;
import io.deeplay.inership.server.Cell;
import io.deeplay.inership.server.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {


    @Test
    public void SetStoneTest() {
        Board board = new Board();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertTrue(board.setStone(new Cell(i, j), Color.BLACK));
            }
        }
        assertFalse(board.isEmptyCell(new Cell(12, 72)));
        assertFalse(board.isEmptyCell(new Cell(5, 5)));
        assertFalse(board.setStone(new Cell(5, 5), Color.WHITE));
    }
}