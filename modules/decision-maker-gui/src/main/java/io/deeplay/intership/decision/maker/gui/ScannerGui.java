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
        size = 9;
    }

    public ScannerGui(int commandType, String login, String password, int rowNumber, int columnNumber, Color color, int gameId){
        this.commandType = commandType;
        this.password = password;
        this.login = login;
        this.color = color;
        withBot = false;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.gameId = gameId;
        turn = true;
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

    public int getSize(){
        return size;
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

    public void setSize(int size){ this.size = size; }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
