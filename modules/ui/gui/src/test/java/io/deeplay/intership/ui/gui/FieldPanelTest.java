package io.deeplay.intership.ui.gui;

import io.deeplay.intership.decision.maker.gui.Action;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.ui.gui.panel.FieldPanel;
import io.deeplay.intership.ui.gui.stuff.Settings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldPanelTest {

    private static FieldPanel fieldPanel;

    @BeforeAll
    public static void createFieldPanel(){
        fieldPanel = new FieldPanel(new DisplayGui(new ScannerGui()), Settings.FIELD_PANEL);
    }
    @Test
    public void setPanelTest(){
        assertDoesNotThrow(() -> fieldPanel.setPanel());
    }
    @Test
    public void gameFieldPanelMoveTest(){
        fieldPanel.displayGui.scannerGui.setColor(Color.BLACK);
        fieldPanel.gameField.setStone(70, 190);
        fieldPanel.displayGui.scannerGui.setColor(Color.WHITE);
        fieldPanel.gameField.setStone(130, 250);
        Color[][] field = fieldPanel.gameField.getField();
        fieldPanel.buttonMove.doClick();
        assertAll(
                () -> assertEquals(fieldPanel.displayGui.scannerGui.getActionType(), Action.MOVE),
                () -> assertEquals(field[0][0], Color.BLACK),
                () -> assertEquals(field[1][1], Color.WHITE)
        );
    }

    @Test
    public void gameFieldPanelPassTest(){
        fieldPanel.buttonPass.doClick();
        assertEquals(fieldPanel.displayGui.scannerGui.getActionType(), Action.SKIP);
    }

    @Test
    public void gameFieldPanelSurrenderTest(){
        fieldPanel.buttonSurrender.doClick();
        assertEquals(fieldPanel.displayGui.scannerGui.getActionType(), Action.SURRENDER);
    }

    @Test
    public void drawFieldTest(){
        Stone[][] stones = new Stone[9][9];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                stones[i][j] = new Stone(Color.BLACK, i, j);
            }
        }
        assertDoesNotThrow(() -> fieldPanel.drawField(stones));
    }
}
