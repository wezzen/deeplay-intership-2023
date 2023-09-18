package io.deeplay.intership.decision.maker.gui;

import io.deeplay.intership.model.Color;

public class ScannerGui {
    private Command commandType;
    private Action actionType;
    private String password;
    private String login;
    private int rowNumber;
    private int columnNumber;
    private String gameId;
    private int size;
    private Color color;
    private boolean turn;
    private boolean withBot;
    private boolean backStyle;

    public ScannerGui() {
        this(Command.NONE, Action.NONE, "", "", -1, -1, Color.EMPTY, "", 9, true);
    }

    public ScannerGui(Command commandType, Action actionType, String login, String password, int rowNumber, int columnNumber, Color color, String gameId, int sizeOfField, boolean backStyle) {
        this.commandType = commandType;
        this.actionType = actionType;
        this.password = password;
        this.login = login;
        this.color = color;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.gameId = gameId;
        turn = true;
        this.backStyle = backStyle;
        size = sizeOfField;
    }

    public Command getCommandType() {
        return commandType;
    }

    public void setCommandType(Command commandType) {
        this.commandType = commandType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getRowNumber() {
        return rowNumber;
    }


    public int getColumnNumber() {
        return columnNumber;
    }

    public String getGameId() {
        return gameId;
    }

    public int getSize() {
        return size;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setRowNumber(int rowNumber){
        this.rowNumber = rowNumber;
    }

    public void setColumnNumber(int columnNumber){
        this.columnNumber = columnNumber;
    }

    public void setSize(int size) { this.size = size; }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean isBackStyle() {
        return backStyle;
    }

    public void setBackStyle(boolean backStyle) {
        this.backStyle = backStyle;
    }

    public Action getActionType() {
        return actionType;
    }

    public void setActionType(Action actionType) {
        this.actionType = actionType;
    }

    public boolean isWithBot() {
        return withBot;
    }

    public void setWithBot(boolean withBot) {
        this.withBot = withBot;
    }
}