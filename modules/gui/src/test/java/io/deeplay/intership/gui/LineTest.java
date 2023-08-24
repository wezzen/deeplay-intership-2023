package io.deeplay.intership.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LineTest {
    @Test
    public void lineTest(){
        Line line = new Line(1,2,3,4);
        assertAll(
                () -> assertEquals(line.x1(), 1),
                () -> assertEquals(line.y1(), 2),
                () -> assertEquals(line.x2(), 3),
                () -> assertEquals(line.y2(), 4)
        );
    }
}
