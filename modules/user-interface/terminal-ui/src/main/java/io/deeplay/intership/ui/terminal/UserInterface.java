package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.model.Stone;

public interface UserInterface {
    public void showLoginActions();
    public void showRoomActions();
    public void showGameActions();
    public void showLogin();
    public void showRegistration();
    public void showColorSelection();
    public void showCreating(int gameId);
    public void showJoin();
    public void showMoveRules();
    public void showBoard(final Stone[][] gameField);
    public void showGameResult(String result);
}
