package io.deeplay.intership.gui;

import io.deeplay.intership.UserInterface;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.model.Stone;
import javax.swing.*;

public class DrawGui implements UserInterface {
    public final JFrame frame;
    public final InitialPanel initialPanel;
    public final EntrancePanel entrancePanel;
    public final StartGamePanel startGamePanel;
    public final CreateGamePanel createGamePanel;
    public final JoinGamePanel joinGamePanel;
    public final GameFieldPanel gameFieldPanel;
    public final ScannerGui scannerGui;

    public DrawGui(ScannerGui scannerGui) {
        frame = new JFrame("GO");
        this.scannerGui = scannerGui;
        gameFieldPanel = new GameFieldPanel(this);
        initialPanel = new InitialPanel(this);
        entrancePanel = new EntrancePanel(this);
        startGamePanel = new StartGamePanel(this);
        createGamePanel = new CreateGamePanel(this);
        joinGamePanel = new JoinGamePanel(this);
    }

    public void start() {
        initialPanel.showPanel();
    }

    public ScannerGui getScannerGui() {
        return scannerGui;
    }

    @Override
    public void showAuthorizationActions() {
        if(!initialPanel.isVisible) {
            initialPanel.showPanel();
            initialPanel.isVisible = true;
        }
    }

    @Override
    public void showRoomActions() {
        if(!startGamePanel.isVisible) {
            startGamePanel.showPanel();
            startGamePanel.isVisible = true;
            initialPanel.isVisible = false;
            gameFieldPanel.isVisible = false;
        }
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
        if(!gameFieldPanel.isVisible) {
            gameFieldPanel.showPanel();
            gameFieldPanel.isVisible = true;
        }
    }

    @Override
    public void showBoard(Stone[][] gameField) {
        if(!gameFieldPanel.isVisible) {
            gameFieldPanel.showPanel();
            gameFieldPanel.isVisible = true;
        }
    }

    @Override
    public void showGameResult(String result) {

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
