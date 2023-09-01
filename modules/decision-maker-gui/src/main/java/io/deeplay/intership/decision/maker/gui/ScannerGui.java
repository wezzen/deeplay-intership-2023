package io.deeplay.intership.decision.maker.gui;

import io.deeplay.intership.model.Color;

public class ScannerGui {
    private int commandType;
    private String password;
    private String login;
    private int rowNumber;
    private int columnNumber;
    private int gameId;
    private int size;
    private int color;
    private boolean turn;
    private boolean backStyle;

    public ScannerGui() {
        commandType = -1;
        password = "";
        login = "";
        color = 3;
        rowNumber = -1;
        columnNumber = -1;
        gameId = -1;
        turn = true;
        this.backStyle = true;
        size = 9;
    }

    public ScannerGui(int commandType, String login, String password, int rowNumber, int columnNumber, Color color, int gameId, boolean backStyle) {
        this.commandType = commandType;
        this.password = password;
        this.login = login;
        this.color = 3;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.gameId = gameId;
        turn = true;
        this.backStyle = backStyle;
        size = 9;
    }

    public int getCommandType() {
        return commandType;
    }

    public void setCommandType(int commandType) {
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

    public int getGameId() {
        return gameId;
    }

    public int getSize() {
        return size;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
}
