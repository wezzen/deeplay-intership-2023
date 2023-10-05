package io.deeplay.intership.ui.gui;

import io.deeplay.intership.ui.gui.stuff.ObjectGui;
import io.deeplay.intership.ui.gui.stuff.RgbColor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ObjectGuiTest {
    @Test
    public void objectGuiTest(){
        RgbColor rgbColor = new RgbColor(0,0,0);
        ObjectGui objectGui = new ObjectGui(1,2, 10,
                rgbColor, "pupa");
        assertAll(
                () -> assertEquals(objectGui.rgbColor(), rgbColor),
                () -> assertEquals(objectGui.x(), 1),
                () -> assertEquals(objectGui.y(), 2),
                () -> assertEquals(objectGui.size(), 10)
        );
    }
}