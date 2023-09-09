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
            case REGISTRATION_OR_JOIN -> registration();
            case LOGIN_OR_CREATE -> login();
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        };
    }

    @Override
    public GameAction getGameAction() throws ClientException {
        return switch (scannerGui.getActionType()){
            case MOVE -> makeMove();
            case SKIP -> skipTurn();
            case SURRENDER -> surrender();
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        };
    }

    @Override
    public GameConfig getGameConfig() throws ClientException {
        return switch (scannerGui.getCommandType()){
            case REGISTRATION_OR_JOIN -> joinGame();
            case LOGIN_OR_CREATE -> createGame();
            default -> throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        };
    }

    @Override
    public Color getColor() throws ClientException {
        Color color = scannerGui.getColor();
        return color == Color.EMPTY ? getRandomColor() : color;
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
        switch (scannerGui.isWithBot() ? 1 : 0){
            case 0 -> bot = false;
            case 1 -> bot = true;
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
