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

    }

    @Override
    public void showRoomActions() {

    }

    @Override
    public void showGameActions() {

    }

    @Override
    public void showLogin() {

    }

    @Override
    public void showRegistration() {

    }

    @Override
    public void showColorSelection() {

    }

    @Override
    public void showCreating(String gameId) {

    }

    @Override
    public void showJoin() {

    }

    @Override
    public void showMoveRules() {

    }

    @Override
    public void showBoard(Stone[][] gameField) {

    }

    @Override
    public void showGameResult(String result) {

    }
}
