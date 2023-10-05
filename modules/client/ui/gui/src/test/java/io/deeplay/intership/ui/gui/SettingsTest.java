package io.deeplay.intership.ui.gui;

import io.deeplay.intership.ui.gui.stuff.Settings;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SettingsTest {

    @Test
    public void settingsTest(){
        assertDoesNotThrow(() -> new Settings());
    }
}
