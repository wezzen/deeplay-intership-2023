package io.deeplay.intership.decision.maker.gui;

import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.decision.maker.GameConfig;
import io.deeplay.intership.decision.maker.LoginPassword;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;

public class DecisionMakerGui implements DecisionMaker {
    private final ScannerGui scannerGui;

    public DecisionMakerGui(ScannerGui scannerGui){
        this.scannerGui = scannerGui;
    }

    @Override
    public LoginPassword getLoginPassword() throws ClientException {
        return switch (scannerGui.getCommandType()){
            case 1 -> registration();
            case 2 -> login();
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        };
    }

    @Override
    public GameAction getGameAction() throws ClientException {
        return switch (scannerGui.getCommandType()){
            case 1 -> makeMove();
            case 2 -> skipTurn();
            case 3 -> surrender();
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        };
    }

    @Override
    public GameConfig getGameConfig() throws ClientException {
        return switch (scannerGui.getCommandType()){
            case 1 -> joinGame();
            case 2 -> createGame();
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        };
    }

    @Override
    public Color getColor() throws ClientException {
        return switch (scannerGui.getCommandType()){
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

    private GameAction surrender(){
        return new GameAction(RequestType.SURRENDER, 0 , 0);
    }

    private GameConfig joinGame() throws ClientException {
        Color color = getColor();
        return new GameConfig(RequestType.JOIN_GAME, false, color, 0, scannerGui.getGameId() + "");
    }

    private GameConfig createGame() throws ClientException {
        boolean bot;
        switch (scannerGui.getCommandType()){
            case 1 -> bot = true;
            case 2 -> bot = false;
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        }
        Color color = getColor();
        return new GameConfig(RequestType.CREATE_GAME, bot, color, scannerGui.getSize(), "");
    }

    private GameAction skipTurn(){
        return new GameAction(RequestType.PASS, 0, 0);
    }

    private GameAction makeMove(){
        return new GameAction(RequestType.TURN, scannerGui.getRowNumber(), scannerGui.getColumnNumber());
    }

    private LoginPassword registration(){
        return new LoginPassword(RequestType.REGISTRATION, scannerGui.getLogin(), scannerGui.getPassword());
    }

    private LoginPassword login(){
        return new LoginPassword(RequestType.LOGIN, scannerGui.getLogin(), scannerGui.getPassword());
    }

    private LoginPassword logout() {
        return new LoginPassword(RequestType.LOGOUT, null, null);
    }
}
