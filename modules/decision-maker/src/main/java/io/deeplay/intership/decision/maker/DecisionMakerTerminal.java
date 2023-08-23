package io.deeplay.intership.decision.maker;

import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.model.Color;

import java.util.Scanner;

public class DecisionMakerTerminal implements DecisionMaker{
    private Scanner scanner = new Scanner(System.in);
    @Override
    public LoginPassword getLoginPassword() {
        return switch (scanner.nextInt()){
            case 1 -> registration();
            case 2 -> autorization();
            default -> throw new IllegalArgumentException();
        };
    }
    @Override
    public GameAction getGameAction() {
        return switch (scanner.nextInt()){
            case 1 -> makeMove();
            case 2 -> skipTurn();
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public GameId getGameId() {
        return switch (scanner.nextInt()){
            case 1 -> joinGame();
            case 2 -> createGame();
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public Color getColor() {
        return switch (scanner.nextInt()){
            case 1 -> Color.WHITE;
            case 2 -> Color.BLACK;
            case 3 -> switch ((int)(Math.random()*2 + 1.0)){
                case 1 -> Color.WHITE;
                case 2 -> Color.BLACK;
                default -> Color.WHITE;
            };
            default -> Color.WHITE;
        };
    }

    private GameId joinGame(){
        return new GameId(RequestType.JOIN_GAME, scanner.nextInt());
    }
    private GameId createGame(){
        return new GameId(RequestType.CREATE_GAME, 0);
    }
    private GameAction skipTurn(){
        return new GameAction(RequestType.PASS, 0, 0);
    }
    public GameAction makeMove(){
        return new GameAction(RequestType.TURN, scanner.nextInt(), scanner.nextInt());
    }
    private LoginPassword registration(){
        return new LoginPassword(RequestType.REGISTRATION, scanner.next(),scanner.next());
    }
    private LoginPassword autorization(){
        return new LoginPassword(RequestType.LOGIN, scanner.next(),scanner.next());
    }
}
