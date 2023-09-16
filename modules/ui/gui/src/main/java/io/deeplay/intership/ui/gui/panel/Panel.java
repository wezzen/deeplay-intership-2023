package io.deeplay.intership.ui.gui.panel;

import io.deeplay.intership.ui.gui.DisplayGui;

import java.awt.event.ActionListener;

public abstract class Panel implements ActionListener {
    private final String name;
    public final DisplayGui displayGui;
    public Panel(DisplayGui displayGui, String name){
        this.displayGui = displayGui;
        this.name = name;
    }
    public void changeSwitch(String panelName, boolean isVis) {
        displayGui.switcher.switchPanel(panelName, isVis);
    }
    abstract public void setPanel();
    abstract public void showPanel();
    abstract public void hidePanel();
}
