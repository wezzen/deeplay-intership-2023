package io.deeplay.intership.decision.maker.terminal;

import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GameId;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.model.Color;

import java.io.IOException;
import java.util.Scanner;

public class DecisionMakerTerminal implements DecisionMaker {
    private final Scanner scanner;

    public DecisionMakerTerminal(Scanner scan) {
        this.scanner = scan;
    }

    @Override
    public LoginPassword getLoginPassword() throws IOException {
        return switch (getChooseNumber()) {
            case 1 -> registration();
            case 2 -> login();
            default -> throw new IOException("THERE IS NO SUCH OPTION");
        };
    }

    @Override
    public GameAction getGameAction() throws IOException {
        return switch (getChooseNumber()) {
            case 1 -> makeMove();
            case 2 -> skipTurn();
            case 3 -> surrender();
            default -> throw new IOException("THERE IS NO SUCH OPTION");
        };
    }

    @Override
    public GameId getGameId() throws IOException {
        return switch (getChooseNumber()) {
            case 1 -> joinGame();
            case 2 -> createGame();
            default -> throw new IOException("THERE IS NO SUCH OPTION");
        };
    }

    @Override
    public Color getColor() throws IOException {
        return switch (getChooseNumber()) {
            case 1 -> Color.WHITE;
            case 2 -> Color.BLACK;
            case 3 -> getRandomColor();
            default -> throw new IOException("THERE IS NO SUCH OPTION");
        };
    }

    private Color getRandomColor() {
        if ((int) (Math.random() * 2 + 1.0) == 1) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    /**
     * Функция возвращает номер выбранного действия от 1 до 9
     *
     * @return INT
     * @throws IOException и сообщение-причину
     */
    private int getChooseNumber() throws IOException {
        String consoleMessage = scanner.next();
        char firstSymbol = consoleMessage.charAt(0);
        if (firstSymbol <= '9' && firstSymbol >= '1') {
            return Integer.parseInt("" + firstSymbol);
        } else {
            throw new IOException("WRONG INPUT");
        }
    }

    private LoginPassword endGame() {
        return new LoginPassword(RequestType.FINISH_GAME, null, null);
    }

    private LoginPassword logout() {
        return new LoginPassword(RequestType.LOGOUT, null, null);
    }

    private GameAction surrender() {
        return new GameAction(RequestType.SURRENDER, 0, 0);
    }

    private GameId joinGame() throws IOException{
        Color color = getColor();
        return new GameId(RequestType.JOIN_GAME, false, color, 0, scanner.nextInt());
    }

    private GameId createGame() throws IOException{
        boolean bot;
        switch (getChooseNumber()) {
            case 1 -> bot = true;
            case 2 -> bot = false;
            default -> throw new IOException("THERE IS NO SUCH OPTION");
        }
        Color color = getColor();
        return new GameId(RequestType.CREATE_GAME, bot, color, scanner.nextInt(), 0);
    }

    private GameAction skipTurn() {
        return new GameAction(RequestType.PASS, 0, 0);
    }

    private GameAction makeMove() {
        return new GameAction(RequestType.TURN, scanner.nextInt(), scanner.nextInt());
    }

    private LoginPassword registration() {
        return new LoginPassword(RequestType.REGISTRATION, scanner.next(), scanner.next());
    }

    private LoginPassword login() {
        return new LoginPassword(RequestType.LOGIN, scanner.next(), scanner.next());
    }
}
