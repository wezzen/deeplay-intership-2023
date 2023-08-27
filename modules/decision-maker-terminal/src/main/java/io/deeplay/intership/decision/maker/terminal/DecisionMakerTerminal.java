package io.deeplay.intership.decision.maker.terminal;

import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GameId;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;

import java.io.IOException;
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
    public GameAction getGameAction() throws ClientException {
        return switch (getChooseNumber()) {
            case 1 -> makeMove();
            case 2 -> skipTurn();
            case 3 -> surrender();
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        };
    }

    @Override
    public GameId getGameId() throws ClientException {
        return switch (getChooseNumber()) {
            case 1 -> joinGame();
            case 2 -> createGame();
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        };
    }

    @Override
    public Color getColor() throws ClientException {
        return switch (getChooseNumber()) {
            case 1 -> Color.WHITE;
            case 2 -> Color.BLACK;
            case 3 -> getRandomColor();
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        };
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
     * @throws IOException и сообщение-причину
     */
    private int getChooseNumber() throws ClientException {
        String consoleMessage = scanner.next();
        char firstSymbol = consoleMessage.charAt(0);
        if (firstSymbol <= '9' && firstSymbol >= '1') {
            return Integer.parseInt("" + firstSymbol);
        } else {
            throw new ClientException(ClientErrorCode.WRONG_INPUT);
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

    private GameId joinGame() throws ClientException {
        Color color = getColor();
        return new GameId(RequestType.JOIN_GAME, false, color, 0, scanner.nextInt());
    }

    private GameId createGame() throws ClientException {
        boolean bot;
        switch (getChooseNumber()) {
            case 1 -> bot = true;
            case 2 -> bot = false;
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
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
