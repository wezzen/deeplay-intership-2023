package io.deeplay.intership.decision.maker;

import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.model.Color;

public class DecisionMakerGui implements DecisionMaker {
    private final ScannerGui scannerGui;

    public DecisionMakerGui(ScannerGui scannerGui){
        this.scannerGui = scannerGui;
    }
    @Override
    public LoginPassword getLoginPassword() {
        return switch (scannerGui.getCommandType()){
            case 1 -> registration();
            case 2 -> login();
            default -> throw new IllegalArgumentException();
        };
    }
    @Override
    public GameAction getGameAction() {
        return switch (scannerGui.getCommandType()){
            case 1 -> makeMove();
            case 2 -> skipTurn();
            case 3 -> surrender();
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public GameId getGameId() {
        return switch (scannerGui.getCommandType()){
            case 1 -> joinGame();
            case 2 -> createGame();
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public Color getColor() {
        return switch (scannerGui.getCommandType()){
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

    private GameAction surrender(){
        return new GameAction(RequestType.SURRENDER, 0 , 0);
    }
    private GameId joinGame(){
        Color color = getColor();
        return new GameId(RequestType.JOIN_GAME, false, color, 0, scannerGui.getGameId());
    }
    private GameId createGame(){
        boolean bot;
        switch (scannerGui.getCommandType()){
            case 1 -> bot = true;
            case 2 -> bot = false;
            default -> bot = false;
        }
        Color color = getColor();
        return new GameId(RequestType.CREATE_GAME, bot, color, scannerGui.getSize(), 0);
    }
    private GameAction skipTurn(){
        return new GameAction(RequestType.PASS, 0, 0);
    }
    public GameAction makeMove(){
        return new GameAction(RequestType.TURN, scannerGui.getRowNumber(), scannerGui.getColumnNumber());
    }
    private LoginPassword registration(){
        return new LoginPassword(RequestType.REGISTRATION, scannerGui.getLogin(), scannerGui.getPassword());
    }
    private LoginPassword login(){
        return new LoginPassword(RequestType.LOGIN, scannerGui.getLogin(), scannerGui.getPassword());
    }

    public LoginPassword logout() {
        return new LoginPassword(RequestType.LOGOUT, null, null);
    }
}
