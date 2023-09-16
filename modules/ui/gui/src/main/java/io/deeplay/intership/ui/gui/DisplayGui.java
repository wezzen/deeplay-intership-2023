package io.deeplay.intership.ui.gui;

import io.deeplay.intership.ui.UserInterface;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.ui.gui.panel.*;
import io.deeplay.intership.ui.gui.stuff.Settings;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DisplayGui implements UserInterface {

    public class Switcher {
        private Map<String, Boolean> isVisible;
        private Map<String, Panel> panels;
        Switcher(){
            isVisible = new HashMap<>();
            panels = new HashMap<>();

            isVisible.put(Settings.CREATE_PANEL, false);
            isVisible.put(Settings.START_PANEL, false);
            isVisible.put(Settings.JOIN_PANEL, false);
            isVisible.put(Settings.INITIAL_PANEL, false);
            isVisible.put(Settings.ENTRANCE_PANEL, false);
            isVisible.put(Settings.FIELD_PANEL, false);
            isVisible.replace(startPanelName, true);
        }

        public Map<String, Panel> getPanels() {
            return panels;
        }

        public void addPanel(String name, Panel panel){
            panels.put(name, panel);
        }

        public boolean checking(String name){
            boolean flag = true;
            for(var key : isVisible.keySet()){
                if(!name.equals(key)) flag = flag && !isVisible.get(key);
            }
            return flag;
        }

        public void switchPanel(String name, boolean isVis){
            if(isVis && checking(name)){
                isVisible.replace(name, true);
                panels.get(name).showPanel();
            }
            else {
                isVisible.replace(name, false);
                panels.get(name).hidePanel();
            }
        }
    }

    private static final String CONFIG_PATH = "modules/ui/gui/src/main/resources/config.properties";

    private String startPanelName;

    public final JFrame frame;
    public final Switcher switcher;

    public final ScannerGui scannerGui;

    public DisplayGui(ScannerGui scannerGui) {
        frame = new JFrame("GO");
        setMode();

        switcher = new Switcher();
        this.scannerGui = scannerGui;

        switcher.addPanel(Settings.INITIAL_PANEL, new InitialPanel(this, Settings.INITIAL_PANEL));
        switcher.addPanel(Settings.ENTRANCE_PANEL, new EntrancePanel(this, Settings.ENTRANCE_PANEL));
        switcher.addPanel(Settings.CREATE_PANEL, new CreateGamePanel(this, Settings.CREATE_PANEL));
        switcher.addPanel(Settings.JOIN_PANEL, new JoinGamePanel(this, Settings.JOIN_PANEL));
        switcher.addPanel(Settings.START_PANEL, new StartGamePanel(this, Settings.START_PANEL));
        switcher.addPanel(Settings.FIELD_PANEL, new FieldPanel(this, Settings.FIELD_PANEL));
    }

    public void setMode(){
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            Properties property = new Properties();
            property.load(fis);

            startPanelName = property.getProperty("display.start");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ScannerGui getScannerGui() {
        return scannerGui;
    }

    @Override
    public void showAuthorizationActions() {
        boolean isShown = switcher.isVisible.get(Settings.INITIAL_PANEL);
        switcher.panels.get(Settings.INITIAL_PANEL).changeSwitch(Settings.INITIAL_PANEL, isShown);
    }

    @Override
    public void showRoomActions() {
        boolean isShown = switcher.isVisible.get(Settings.START_PANEL);
        switcher.panels.get(Settings.START_PANEL).changeSwitch(Settings.START_PANEL, isShown);
    }

    @Override
    public void showGameActions() {

    }

    @Override
    public void showLogin() {
        showMessage("Вход", "Добро пожаловать на сервер!");
    }

    @Override
    public void showRegistration() {
        showMessage("Вход", "Вы зарегистрировались на сервере!");
    }

    @Override
    public void showColorSelection() {

    }

    @Override
    public void showCreating(String gameId) {
        showMessage("Вход", "Создана игровая сессия под номером: " + gameId);
    }

    @Override
    public void showJoin() {
        showMessage("Вход", "Вы присоединились к игре!");
    }

    @Override
    public void showMoveRules() {

    }

    @Override
    public void showBoard(Stone[][] gameField) {
        switch(startPanelName){
            case Settings.INITIAL_PANEL -> switcher.panels.get(
                    Settings.START_PANEL).changeSwitch(
                    Settings.START_PANEL, switcher.isVisible.get(
                            Settings.START_PANEL));
            case Settings.FIELD_PANEL -> switcher.getPanels().get(
                    Settings.FIELD_PANEL).drawField(gameField);
        }
    }

    @Override
    public void showGameResult(String result) {
        JOptionPane pane = new JOptionPane(result,
                JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(null, "Info");
        dialog.setModal(false);
        dialog.setVisible(true);

        new Timer(3000, e -> dialog.setVisible(false)).start();
    }

    public void showMessage(String title, String message){
        JOptionPane pane = new JOptionPane(message,
                JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(null, title);
        dialog.setModal(false);
        dialog.setVisible(true);

        new Timer(2000, e -> dialog.setVisible(false)).start();
    }
}