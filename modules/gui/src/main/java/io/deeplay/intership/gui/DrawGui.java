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

    public DrawGui() {
        frame = new JFrame("GO");
        scannerGui = new ScannerGui();
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

    @Override
    public void showAuthorizationActions() {
        initialPanel.showPanel();
    }

    @Override
    public void showRoomActions() {
        startGamePanel.showPanel();
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
        gameFieldPanel.showPanel();
    }

    @Override
    public void showBoard(Stone[][] gameField) {
        gameFieldPanel.showPanel();
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
