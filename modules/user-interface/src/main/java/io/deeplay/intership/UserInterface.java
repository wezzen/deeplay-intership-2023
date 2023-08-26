package io.deeplay.intership;

import io.deeplay.intership.model.Stone;

public interface UserInterface {
    void showAuthorizationActions();

    void showRoomActions();

    void showGameActions();

    void showLogin();

    void showRegistration();

    void showColorSelection();

    void showCreating(int gameId);

    void showJoin();

    void showMoveRules();

    void showBoard(final Stone[][] gameField);

    void showGameResult(String result);
}
