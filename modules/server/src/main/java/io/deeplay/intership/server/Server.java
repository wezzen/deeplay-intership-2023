package io.deeplay.intership.server;

import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.game.Game;
import io.deeplay.intership.ui.terminal.UserPlayerActions;

public class Server {
    public static void main(final String[] args) {
        Game game = new Game();
        Board board = game.startGame();
        UserPlayerActions playerBlack = new UserPlayerActions();
        playerBlack.setColor(Color.BLACK);
        UserPlayerActions playerWhite = new UserPlayerActions();
        playerWhite.setColor(Color.WHITE);

        while (!game.gameIsOver()) {
            System.out.println("Ход черных");
            game.analyzeMove(playerBlack.chooseGameAction(board));

            System.out.println("Ход белых");
            game.analyzeMove(playerWhite.chooseGameAction(board));
        }
        game.endGame();
    }

}
