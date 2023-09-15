package io.deeplay.intership.selfplay.bot;

import io.deeplay.intership.bot.random.RandomBot;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GoPlayer;
import io.deeplay.intership.exception.game.GameException;
import io.deeplay.intership.game.Game;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Score;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.ui.UserInterface;
import io.deeplay.intership.ui.gui.DrawGui;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Selfplay {
    private static final Logger log = Logger.getLogger(Selfplay.class);
    private static final int gamesCount = 5;
    private static final int PLAYERS_COUNT = 2;
    private static int countOfBlackWins = 0;
    private final GoPlayer[] players = new GoPlayer[PLAYERS_COUNT];
    private final UserInterface display;
    private final Game game;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < gamesCount; i++) {
            String startMessage = String.format("Game %d was started", i);
            String endMessage = String.format("Game %d was ended", i);

            log.info(startMessage);
            new Selfplay().startGame();
            Thread.sleep(10);
            log.info(endMessage);
        }
    }

    public Selfplay() {
        players[0] = new RandomBot("Bot black", Color.BLACK);
        players[1] = new RandomBot("Bot white", Color.WHITE);
        display = new DrawGui(new ScannerGui());
        game = new Game();
    }

    public void startGame() {
        int currentPlayer = 0;
        while (!game.gameIsOver()) {
            try {
                var field = getMove(players[currentPlayer]);
                display.showBoard(field);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                currentPlayer = (currentPlayer + 1) % 2;
            } catch (GameException e) {
                log.debug("Invalid move");
            }
        }
        gameResult(game.getGameScore());
    }

    private Stone[][] getMove(final GoPlayer player) throws GameException {
        if (player instanceof RandomBot){
            ((RandomBot) player).setGameField(game.getGameField());
        }
        else{
            ((RandomBot) player).setGameField(game.getGameField());
        }
        final GameAction gameAction = player.getGameAction();
        return switch (gameAction.type()) {
            case TURN -> turn(gameAction, player.getColor());
            case PASS -> pass(player.getColor());
            default -> throw new IllegalStateException("Unexpected value: " + gameAction.type());
        };
    }

    private Stone[][] turn(final GameAction gameAction, final Color color) throws GameException {
        return game.makeMove(new Stone(color, gameAction.row(), gameAction.column()));
    }

    private Stone[][] pass(final Color color) throws GameException {
        return game.skipTurn(color);
    }

    private void gameResult(final Score score) {
        if(score.blackPoints() > score.whitePoints()){
            countOfBlackWins++;
        }
        final String result = String.format("Черные: %f очков \nБелые: %f очков", score.blackPoints(), score.whitePoints());
        display.showGameResult(result);
    }

    public void showAllResults(){
        final String result = String.format("Черные: %d игр \nБелые: %d игр", countOfBlackWins, gamesCount-countOfBlackWins);
        display.showGameResult(result);
    }
}
