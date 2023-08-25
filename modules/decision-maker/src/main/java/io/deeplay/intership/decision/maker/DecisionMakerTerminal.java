package io.deeplay.intership.decision.maker;

import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.model.Color;

import java.util.Scanner;

public class DecisionMakerTerminal implements DecisionMaker {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public LoginPassword getLoginPassword() {
        return switch (scanner.nextInt()) {
            case 1 -> registration();
            case 2 -> login();
            default -> login();
        };
    }

    @Override
    public GameAction getGameAction() {
        return switch (scanner.nextInt()) {
            case 1 -> makeMove();
            case 2 -> skipTurn();
            case 3 -> surrender();
            default -> skipTurn();
        };
    }

    @Override
    public GameId getGameId() {
        return switch (scanner.nextInt()) {
            case 1 -> joinGame();
            case 2 -> createGame();
            default -> createGame();
        };
    }

    @Override
    public Color getColor() {
        return switch (scanner.nextInt()) {
            case 1 -> Color.WHITE;
            case 2 -> Color.BLACK;
            case 3 -> switch ((int) (Math.random() * 2 + 1.0)) {
                case 1 -> Color.WHITE;
                case 2 -> Color.BLACK;
                default -> Color.WHITE;
            };
            default -> Color.WHITE;
        };
    }

    public LoginPassword endGame() {
        return new LoginPassword(RequestType.FINISH_GAME, null, null);
    }

    public LoginPassword logout() {
        return new LoginPassword(RequestType.LOGOUT, null, null);
    }

    private GameAction surrender() {
        return new GameAction(RequestType.SURRENDER, 0, 0);
    }

    private GameId joinGame() {
        Color color = getColor();
        return new GameId(RequestType.JOIN_GAME, false, color, 0, scanner.nextInt());
    }

    private GameId createGame() {
        boolean bot;
        switch (scanner.nextInt()) {
            case 1 -> bot = true;
            case 2 -> bot = false;
            default -> bot = false;
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
