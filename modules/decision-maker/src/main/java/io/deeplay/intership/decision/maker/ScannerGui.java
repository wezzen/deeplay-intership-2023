package io.deeplay.intership.decision.maker;

import io.deeplay.intership.model.Color;

public class ScannerGui {
    private int commandType;
    private String password;
    private String login;
    private int size;
    private int rowNumber;
    private int columnNumber;
    private int gameId;
    private boolean withBot;
    private Color color;
    private boolean turn;

    public ScannerGui(){
        commandType = 0;
        password = "";
        login = "";
        color = Color.EMPTY;
        withBot = false;
        rowNumber = -1;
        columnNumber = -1;
        gameId = -1;
        turn = true;
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

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public boolean isWithBot() {
        return withBot;
    }

    public void setWithBot(boolean withBot) {
        this.withBot = withBot;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
