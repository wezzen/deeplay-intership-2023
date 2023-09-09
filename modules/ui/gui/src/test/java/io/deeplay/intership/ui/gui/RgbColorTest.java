package io.deeplay.intership.ui.gui;

import io.deeplay.intership.ui.gui.RgbColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RgbColorTest {
    @Test
    public void rgbTest(){
        RgbColor rgbColor = new RgbColor(5, 6, 7);
        assertAll(
                () -> assertEquals(rgbColor.red(), 5),
                () -> assertEquals(rgbColor.green(), 6),
                () -> assertEquals(rgbColor.blue(), 7)
        );
    }
}
