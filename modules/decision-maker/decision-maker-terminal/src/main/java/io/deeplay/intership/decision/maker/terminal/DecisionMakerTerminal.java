package io.deeplay.intership.decision.maker.terminal;

import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;

import java.util.Scanner;

public class DecisionMakerTerminal implements DecisionMaker {
    private final Scanner scanner;

    public DecisionMakerTerminal(Scanner scan) {
        this.scanner = scan;
    }

    @Override
    public LoginPassword getLoginPassword() throws ClientException {
        return switch (getChooseNumber()) {
            case 1 -> registration();
            case 2 -> login();
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        };
    }

    @Override
    public GameAction getGameAction() {
        return switch (getChooseNumber()) {
            case 1 -> makeMove();
            case 2 -> skipTurn();
            case 3 -> surrender();
            default -> throw new IllegalStateException(ClientErrorCode.NO_SUCH_OPTIONS.message);
        };
    }

    @Override
    public GameConfig getGameConfig() throws ClientException {
        return switch (getChooseNumber()) {
            case 1 -> joinGame();
            case 2 -> createGame();
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        };
    }

    @Override
    public Color getColor() {
        return switch (getChooseNumber()) {
            case 1 -> Color.WHITE;
            case 2 -> Color.BLACK;
            case 3 -> getRandomColor();
            default -> throw new IllegalStateException(ClientErrorCode.NO_SUCH_OPTIONS.message);
        };
    }

    @Override
    public void startGame() {

    }

    @Override
    public void endGame() {

    }

    private Color getRandomColor() {
        if (Math.random() > 0.5) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    /**
     * Функция возвращает номер выбранного действия от 1 до 9
     *
     * @return INT
     * @throws IllegalStateException и сообщение-причину
     */
    private int getChooseNumber() {
        String consoleMessage = scanner.next();
        char firstSymbol = consoleMessage.charAt(0);
        if (firstSymbol <= '9' && firstSymbol >= '1') {
            return Integer.parseInt("" + firstSymbol);
        } else {
            throw new IllegalStateException(ClientErrorCode.WRONG_INPUT.message);
        }
    }

    private LoginPassword logout() {
        return new LoginPassword(RequestType.LOGOUT, null, null);
    }

    private GameAction surrender() {
        return new GameAction(RequestType.SURRENDER, 0, 0);
    }

    private GameConfig joinGame() {
        Color color = getColor();
        return new GameConfig(RequestType.JOIN_GAME, false, color, 0, scanner.next());
    }

    private GameConfig createGame() throws ClientException {
        boolean bot;
        Color color = getColor();
        switch (getChooseNumber()) {
            case 1 -> bot = true;
            case 2 -> bot = false;
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        }
        return new GameConfig(RequestType.CREATE_GAME, bot, color, scanner.nextInt(), "");
    }

    private GameAction skipTurn() {
        return new GameAction(RequestType.PASS, 0, 0);
    }

    private GameAction makeMove() {
        return new GameAction(RequestType.TURN, scanner.nextInt() - 1, scanner.nextInt() - 1);
    }

    private LoginPassword registration() {
        return new LoginPassword(RequestType.REGISTRATION, scanner.next(), scanner.next());
    }

    private LoginPassword login() {
        return new LoginPassword(RequestType.LOGIN, scanner.next(), scanner.next());
    }
}
