package io.intership.deeplay.minimax.bot;

import io.deeplay.intership.bot.Bot;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.game.CheckGameOver;
import io.deeplay.intership.game.GroupControl;
import io.deeplay.intership.game.ScoreCalculator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.deeplay.intership.model.*;
import io.deeplay.intership.ui.terminal.Display;
import io.deeplay.intership.validation.Validation;

public class MinMaxBot extends Bot {

    private final CheckGameOver gameCheckOver;
    private Board board;
    private Display display = new Display();
    private int rowTurn;
    private int columnTurn;
    private int counter;
    private final int SIZE = 9;
    private Color enemyColor;
    private Set<GameAction> possibleActions;

    private int weights[][] = new int[][]{{1,1,5,15,20,15,5,1,1},{1,5,15,20,10,20,15,5,1},{5,15,20,10,5,10,20,15,5},{15,20,10,5,1,5,10,20,15},{20,10,5,1,1,1,5,10,20},{15,20,10,5,1,5,10,20,15},{5,15,20,10,5,10,20,15,5},{1,5,15,20,10,20,15,5,1},{1,1,5,15,20,15,5,1,1}};

    public record TurnAndEstimation(GameAction gameAction, Double utility) {}

    public MinMaxBot(String name, Color color) {
        super(name, color);
        enemyColor = Color.invertColor(color);
        board = new Board();
        gameField = board.getField();
        gameCheckOver = new CheckGameOver();
        possibleActions = new HashSet<>();
        counter = 10;
    }

    public GameAction makeMove(Stone[][] field) throws ClientException {
        /*if(!gameCheckOver.canMakeMove(color)){
            System.out.println("*******");
            throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        }*/

        possibleActions = getPossibleActions(field);
        counter--;

        Stone[][] tempField = getCopyOfField(field);

        //createGroups(field);
        makeGroups(tempField, color);
        makeGroups(tempField, enemyColor);

        var turnAndEstimation = executeMinMax(3, Double.MIN_VALUE,
                Double.MAX_VALUE, tempField, color, color, null,
                new HashSet<>(possibleActions));

        this.rowTurn = turnAndEstimation.gameAction.row();
        this.columnTurn = turnAndEstimation.gameAction.column();
        possibleActions.remove(turnAndEstimation.gameAction);
        return turnAndEstimation.gameAction;
    }

    public TurnAndEstimation executeMinMax(int depth, double alpha, double beta,
                                           Stone[][] field, Color currentColor,
                                           Color agentColor, GameAction lastCell,
                                           Set<GameAction> possibleActions) {
        boolean gameOver = !gameCheckOver.hasYetStones(currentColor);
        if(depth == 0 || gameOver) {
            if(lastCell == null) {
                GameAction action = new GameAction(RequestType.PASS, 0, 0);
                return new TurnAndEstimation(action, getSimpleEstimation(field) + getFinalEstimation(field, currentColor));
            }
            return new TurnAndEstimation(lastCell,
                    getSimpleEstimation(field) + getFinalEstimation(field, currentColor) + ifKill(field, lastCell));
        }

        var isMax = currentColor == agentColor;
        var turnAndEstimation = new TurnAndEstimation(null, isMax ? Double.MIN_VALUE : Double.MAX_VALUE);

        for(GameAction action : possibleActions) {

            var tempField = getCopyOfField(field);
            tempField[action.row()][action.column()].setColor(currentColor);

            var tempPossibleCells = new HashSet<>(possibleActions);
            tempPossibleCells.remove(action);

            //createGroups(field);
            makeGroups(tempField, color);
            makeGroups(tempField, enemyColor);

            var value = executeMinMax(depth-1, alpha, beta,
                    tempField, Color.invertColor(currentColor), agentColor,
                    action, tempPossibleCells);

            if(isMax) {
                alpha = Double.max(alpha, value.utility);
                if (turnAndEstimation.utility <= value.utility + ifKill(tempField, action)) {
                    turnAndEstimation = new TurnAndEstimation(action, value.utility + ifKill(tempField, action));
                }
            }
            else {
                beta = Double.min(beta, value.utility());
                if (turnAndEstimation.utility >= value.utility) {
                    turnAndEstimation = new TurnAndEstimation(action, value.utility);
                }
            }
            if(beta <= alpha) {
                break;
            }
        }
        return turnAndEstimation;
    }

    public void createGroups(Stone[][] field) {
        MakeGroups[] makeGroups = new MakeGroups[2];
        makeGroups[0] = new MakeGroups();
        makeGroups[1] = new MakeGroups();
        makeGroups[0].setProperties(field, Color.BLACK);
        makeGroups[1].setProperties(field, Color.WHITE);
        makeGroups[0].start();
        makeGroups[1].start();
        try {
            makeGroups[0].join();
            makeGroups[1].join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public GameAction getGameAction() {
        boolean canMove = false;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length && !canMove; j++) {
                Stone stone = gameField[i][j];
                if (stone.getColor() == Color.EMPTY) {
                    canMove = true;
                }
            }
        }
        if (canMove) {
            try {
                return makeMove(gameField);
            } catch (ClientException e) {
                throw new RuntimeException(e);
            }
        } else {
            return skipTurn();
        }
    }

    @Override
    public void startGame() {

    }

    @Override
    public void endGame() {

    }

    public double getFinalEstimation(Stone[][] field, Color agentColor){
        ScoreCalculator scoreCalculator = new ScoreCalculator(field);
        Score score = scoreCalculator.getTotalScore();
        return (switch(agentColor){
            case BLACK -> score.blackPoints() - score.whitePoints();
            case WHITE -> score.whitePoints() - score.blackPoints();
            default -> throw new IllegalStateException("Unexpected value: " + agentColor);
        });
    }

    public double getSimpleEstimation(Stone[][] field) {
        int n = field.length;
        double result = 0.0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(field[i][j].getColor() == color) {
                    result += weights[i][j];
                }
            }
        }
        return result;
    }

    public boolean isIn(int x, int y) {
        return (x >= 0 && x <= 8 && y >= 0 && y <= 8);
    }

    public double isInside(Stone[][] field, GameAction action) {
        int x = action.row();
        int y = action.column();
        double result = 0.0;
        int area = getArea(action);
        if(isIn(x+1, y) && field[x+1][y].getColor() == color) {
            if(area == 0 && isIn(x, y+1) && field[x][y+1].getColor() == color) {
                result -= 15;
            }
        }
        if(isIn(x+1, y) && field[x+1][y].getColor() == color) {
            if(area == 1 && isIn(x, y-1) && field[x][y-1].getColor() == color) {
                result -= 15;
            }
        }
        if(isIn(x-1, y) && field[x-1][y].getColor() == enemyColor) {
            if(area == 3 && isIn(x, y-1) && field[x][y-1].getColor() == color) {
                result -= 15;
            }
        }
        if(isIn(x-1, y) && field[x-1][y].getColor() == enemyColor) {
            if(area == 2 && isIn(x, y+1) && field[x][y+1].getColor() == color) {
                result -= 15;
            }
        }
        return result;
    }

    public int getArea(GameAction action) {
        int x = action.row();
        int y = action.column();
        if(x <= 4 && y <= 4) {
            return 0;
        }
        if(x <= 4 && y > 4) {
            return 1;
        }
        if(x > 4 && y <= 4) {
            return 2;
        }
        if(x > 4 && y > 4) {
            return 3;
        }
        return -1;
    }

    public double ifKill(Stone[][] field, GameAction action) {
        int x = action.row();
        int y = action.column();
        double result = 0.0;
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if(Math.abs(i-j) == 1 && isIn(x+i, y+j)) {
                    int dames = field[x+i][y+j].getGroup().getDamesCount();
                    //System.out.println("Look: " + dames + " " + (x+i) + " " + (y+j));
                    //display.showBoard(field);
                    if(field[x+i][y+j].getColor() == enemyColor && dames <= 2) {
                        System.out.println("Look: " + dames + " " + (x+i) + " " + (y+j));
                        result += 50;
                        if(dames == 1){
                            result += 10 * field[x+i][y+j].getGroup().getStonesCount();
                        }
                    }
                }
            }
        }
        return result;
    }

    public double addLines(Stone[][] field, GameAction action) {
        int x = action.row();
        int y = action.column();
        return 0.0;
    }

    public double addNeighbour(Stone[][] field, GameAction action) {
        int x = action.row();
        int y = action.column();
        if(isIn(x-1, y+1) && field[x-1][y+1].getColor() == field[x][y].getColor()) {
            if(isIn(x+1, y-1) && field[x+1][y-1].getColor() == field[x][y].getColor()) {
                return 15;
            }
            else {
                return 10;
            }
        }
        if(isIn(x-1, y-1) && field[x-1][y-1].getColor() == field[x][y].getColor()) {
            if(isIn(x+1, y+1) && field[x+1][y+1].getColor() == field[x][y].getColor()) {
                return 15;
            }
            else {
                return 10;
            }
        }
        if(isIn(x-1, y) && field[x-1][y].getColor() == field[x][y].getColor()) {
            if(isIn(x+1, y) && field[x+1][y].getColor() == field[x][y].getColor()) {
                return 10;
            }
            else {
                return 5;
            }
        }
        if(isIn(x, y-1) && field[x][y-1].getColor() == field[x][y].getColor()) {
            if(isIn(x, y+1) && field[x][y+1].getColor() == field[x][y].getColor()) {
                return 10;
            }
            else {
                return 5;
            }
        }
        return 0;
    }

    public double catchStones(Stone[][] field, GameAction action) {
        int x = action.row();
        int y = action.column();
        double result = 0.0;
        if(y > 0 && field[x][y-1].getColor() == enemyColor) {
            if (x > 0 && y > 0 && field[x-1][y-1].getColor() == color) {
                result += 5;
            }
            if (x < 8 && y > 0 && field[x+1][y-1].getColor() == color) {
                result += 5;
            }
        }
        if(y < 8 && field[x][y+1].getColor() == enemyColor) {
            if (x > 0 && y < 8 && field[x-1][y+1].getColor() == color) {
                result += 5;
            }
            if (x < 8 && y < 8 && field[x+1][y+1].getColor() == color) {
                result += 5;
            }
        }
        if(x > 0 && field[x-1][y].getColor() == enemyColor) {
            if (x > 0 && y > 0 && field[x-1][y-1].getColor() == color) {
                result += 5;
            }
            if (x > 0 && y < 8 && field[x-1][y+1].getColor() == color) {
                result += 5;
            }
        }
        if(x < 8 && field[x+1][y].getColor() == enemyColor) {
            if (x < 8 && y > 0 && field[x+1][y-1].getColor() == color) {
                result += 5;
            }
            if (x < 8 && y < 8 && field[x+1][y+1].getColor() == color) {
                result += 5;
            }
        }

        return result;
    }

    public List<GameAction> getCopyOfPossibleActions(List<GameAction> actions){
        List<GameAction> possibleActions = new ArrayList<>();
        for(GameAction action : actions){
            possibleActions.add(new GameAction(RequestType.TURN, action.row(), action.column()));
        }
        return possibleActions;
    }

    Stone[][] getCopyOfField(Stone[][] field){
        int n = field.length;
        Stone[][] newField = new Stone[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                Stone stone = field[i][j];
                newField[i][j] = new Stone(stone.getColor(),
                        stone.getRowNumber(), stone.getColumnNumber(), null);
                newField[i][j].setGroup(stone.getGroup());
            }
        }
        return newField;
    }

    public Set<GameAction> getPossibleActions(Stone[][] field){
        Set<GameAction> possibleActions = new HashSet<>();
        Validation validation = new Validation(board);
        int n = field.length;
        if(counter > 0){
            possibleActions = getSet(field, (int)(4 * Math.random()));
        }
        else {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (field[i][j].getColor() == Color.EMPTY &&
                            validation.isCorrectMove(field[i][j].getColor(), field[i][j].getRowNumber(), field[i][j].getColumnNumber())) {
                        possibleActions.add(new GameAction(RequestType.TURN, i, j));
                    }
                }
            }
        }
        return possibleActions;
    }

    public Set<GameAction> getSet(Stone[][] field, int quarter) {
        Set<GameAction> setOfQuarter = new HashSet<>();
        Validation validation = new Validation(board);
        if(quarter == 0) {
            for(int i = 0; i < SIZE / 2; i++){
                for(int j = 0; j < SIZE / 2; j++){
                    if(field[i][j].getColor() == Color.EMPTY &&
                            validation.isCorrectMove(field[i][j].getColor(), field[i][j].getRowNumber(), field[i][j].getColumnNumber())){
                        setOfQuarter.add(new GameAction(RequestType.TURN, i, j));
                    }
                }
            }
            return setOfQuarter;
        }
        else if(quarter == 1) {
            for(int i = 0; i < SIZE / 2; i++){
                for(int j = SIZE / 2; j < SIZE; j++){
                    if(field[i][j].getColor() == Color.EMPTY &&
                            validation.isCorrectMove(field[i][j].getColor(), field[i][j].getRowNumber(), field[i][j].getColumnNumber())){
                        setOfQuarter.add(new GameAction(RequestType.TURN, i, j));
                    }
                }
            }
            return setOfQuarter;
        }
        else if(quarter == 2) {
            for(int i = SIZE / 2; i < SIZE; i++){
                for(int j = 0; j < SIZE / 2; j++){
                    if(field[i][j].getColor() == Color.EMPTY &&
                            validation.isCorrectMove(field[i][j].getColor(), field[i][j].getRowNumber(), field[i][j].getColumnNumber())){
                        setOfQuarter.add(new GameAction(RequestType.TURN, i, j));
                    }
                }
            }
            return setOfQuarter;
        }
        else {
            for(int i = SIZE / 2; i < SIZE; i++){
                for(int j = SIZE / 2; j < SIZE; j++){
                    if(field[i][j].getColor() == Color.EMPTY &&
                            validation.isCorrectMove(field[i][j].getColor(), field[i][j].getRowNumber(), field[i][j].getColumnNumber())){
                        setOfQuarter.add(new GameAction(RequestType.TURN, i, j));
                    }
                }
            }
            return setOfQuarter;
        }
    }

    List<Stone[][]> getCopyOfGameHistory(List<Stone[][]> gameHistory){
        List<Stone[][]> newGameHistory = new ArrayList<>();
        int n = gameHistory.size();
        for(int i = 0; i < n; i++){
            newGameHistory.add(getCopyOfField(gameHistory.get(i)));
        }
        return newGameHistory;
    }

    public void printDames(Stone[][] field) {
        int n = field.length;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.print(field[i][j].getGroup().getDamesCount() + " ");
            }
            System.out.println();
        }
    }

    public void makeGroups(Stone[][] field, Color color) {
        int n = field.length;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(field[i][j].getColor() == color && field[i][j].getGroup() == null) {
                    open(field, i, j, color, new Group());
                }
            }
        }
    }

    public void open(Stone[][] field, int i, int j, Color color, Group group) {
        field[i][j].setGroup(group);
        group.addStone(field[i][j]);
        if(i > 0) {
            if(Color.EMPTY == field[i-1][j].getColor()) {
                group.addFreeCell(field[i-1][j]);
            }
            else if(color == field[i-1][j].getColor() && field[i-1][j].getGroup() == null) {
                open(field, i - 1, j, color, group);
            }
        }
        if(j > 0) {
            if(Color.EMPTY == field[i][j-1].getColor()) {
                group.addFreeCell(field[i][j-1]);
            }
            else if(color == field[i][j-1].getColor() && field[i][j-1].getGroup() == null) {
                open(field, i, j - 1, color, group);
            }
        }
        if(i < 8) {
            if(Color.EMPTY == field[i+1][j].getColor()) {
                group.addFreeCell(field[i+1][j]);
            }
            else if(color == field[i+1][j].getColor() && field[i+1][j].getGroup() == null) {
                open(field, i + 1, j, color, group);
            }
        }
        if(j < 8) {
            if(Color.EMPTY == field[i][j+1].getColor()) {
                group.addFreeCell(field[i][j+1]);
            }
            else if(color == field[i][j+1].getColor() && field[i][j+1].getGroup() == null) {
                open(field, i, j + 1, color, group);
            }
        }
    }

    private GameAction skipTurn() {
        return new GameAction(RequestType.PASS, 0, 0);
    }
}
