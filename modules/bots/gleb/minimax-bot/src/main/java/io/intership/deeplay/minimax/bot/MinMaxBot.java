package io.intership.deeplay.minimax.bot;

import io.deeplay.intership.bot.Bot;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.game.CheckGameOver;
import io.deeplay.intership.game.ScoreCalculator;

import java.util.ArrayList;
import java.util.List;

import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Score;
import io.deeplay.intership.model.Stone;
import io.deeplay.intership.ui.terminal.Display;
import io.deeplay.intership.validation.Validation;
import org.apache.log4j.Logger;

public class MinMaxBot extends Bot {

    private final CheckGameOver gameCheckOver;
    private Board board;
    private Display display = new Display();
    private int action;
    private int rowTurn;
    private int columnTurn;
    private double currentCount;
    private Color enemyColor;
    private Logger logger;

    private int weights[][] = new int[][]{{1,1,5,15,20,15,5,1,1},{1,5,15,20,10,20,15,5,1},{5,15,20,10,5,10,20,15,5},{15,20,10,5,1,5,10,20,15},{20,10,5,1,1,1,5,10,20},{15,20,10,5,1,5,10,20,15},{5,15,20,10,5,10,20,15,5},{1,5,15,20,10,20,15,5,1},{1,1,5,15,20,15,5,1,1}};

    public record TurnAndEstimation(GameAction gameAction, Double utility) {}

    public MinMaxBot(String name, Color color) {
        super(name, color);
        currentCount = 0;
        enemyColor = Color.invertColor(color);
        board = new Board();
        gameField = board.getField();
        gameCheckOver = new CheckGameOver();
        logger = Logger.getLogger(MinMaxBot.class);
        //action = 1;
    }

    public GameAction makeMove(Stone[][] field) throws ClientException {
        /*if(!gameCheckOver.canMakeMove(color)){
            System.out.println("*******");
            throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        }*/

        display.showBoard(field);
        List<Stone[][]> history = new ArrayList<>();
        history.add(getCopyOfField(field));

        var turnAndEstimation = executeMinMax(2, Double.MIN_VALUE,
                Double.MAX_VALUE, getCopyOfField(field), color, color, null,
                getPossibleActions(field), history);
        this.rowTurn = turnAndEstimation.gameAction.row();
        this.columnTurn = turnAndEstimation.gameAction.column();
        return turnAndEstimation.gameAction;
    }

    public TurnAndEstimation executeMinMax(int depth, double alpha, double beta,
                                           Stone[][] field, Color currentColor,
                                           Color agentColor, GameAction lastCell,
                                           List<GameAction> possibleActions,
                                           List<Stone[][]> history) {
        boolean gameOver = !gameCheckOver.hasYetStones(currentColor);
        if(depth == 0 || gameOver) {
            if(lastCell == null) {
                GameAction action = new GameAction(RequestType.PASS, 0, 0);
                return new TurnAndEstimation(action, getSimpleEstimation(field) + getFinalEstimation(field, currentColor));
            }
            return new TurnAndEstimation(lastCell,
                    getSimpleEstimation(field) + getFinalEstimation(field, currentColor));
        }

        var isMax = currentColor == agentColor;
        var turnAndEstimation = new TurnAndEstimation(null, isMax ? Double.MIN_VALUE : Double.MAX_VALUE);

        if(possibleActions.isEmpty()) {
            return turnAndEstimation;
        }

        for(GameAction action : possibleActions) {

            var tempField = getCopyOfField(field);
            tempField[action.row()][action.column()].setColor(currentColor);
            var tempHistory = getCopyOfGameHistory(history);
            tempHistory.add(tempField);
            var tempPossibleCells = getCopyOfPossibleActions(possibleActions);
            tempPossibleCells.remove(action);

            var value = executeMinMax(depth-1, alpha, beta,
                    tempField, Color.invertColor(currentColor), agentColor,
                    action, tempPossibleCells, tempHistory);
            //tempField[action.row()][action.column()].setColor(Color.EMPTY);
            if(isMax) {
                alpha = Double.max(alpha, value.utility);
                //System.out.println("Max = " + turnAndEstimation.utility + value.utility);
                if (turnAndEstimation.utility <= value.utility) {
                    turnAndEstimation = new TurnAndEstimation(action, value.utility + addNeighbour(tempField, action) + ifKill(tempField, action) + isInside(tempField, action));
                }
            }
            else {
                beta = Double.min(beta, value.utility());
                //System.out.println("Min = " + turnAndEstimation.utility + value.utility);
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
        if(isIn(x+1, y) && field[x+1][y].getColor() == color) {
            if(isIn(x, y+1) && field[x][y+1].getColor() == color) {
                result -= 15;
                logger.debug("Enemy near you!");
            }
        }
        if(isIn(x+1, y) && field[x+1][y].getColor() == color) {
            if(isIn(x, y-1) && field[x][y-1].getColor() == color) {
                result -= 15;
                logger.debug("Enemy near you!");
            }
        }
        if(isIn(x-1, y) && field[x-1][y].getColor() == enemyColor) {
            if(isIn(x, y-1) && field[x][y-1].getColor() == color) {
                result -= 15;
                logger.debug("Enemy near you!");
            }
        }
        if(isIn(x-1, y) && field[x-1][y].getColor() == enemyColor) {
            if(isIn(x, y+1) && field[x][y+1].getColor() == color) {
                result -= 15;
                logger.debug("Enemy near you!");
            }
        }
        return result;
    }

    public double ifKill(Stone[][] field, GameAction action) {
        int x = action.row();
        int y = action.column();
        double result = 0.0;
        if(isIn(x-1, y) && field[x-1][y].getColor() == enemyColor) {
            int dames = field[x-1][y].getGroup().getDamesCount();
            if(dames <= 2) {
                if(dames == 1) {
                    result += 100;
                }
                else {
                    result += 45;
                }
                logger.debug("Enemy near you!");
            }
        }
        if(isIn(x+1, y) && field[x+1][y].getColor() == enemyColor) {
            int dames = field[x+1][y].getGroup().getDamesCount();
            if(dames <= 2) {
                if(dames == 1) {
                    result += 100;
                }
                else {
                    result += 45;
                }
                logger.debug("Enemy near you!");
            }
        }
        if(isIn(x, y-1) && field[x][y-1].getColor() == enemyColor) {
            int dames = field[x][y-1].getGroup().getDamesCount();
            if(dames <= 2) {
                if(dames == 1) {
                    result += 100;
                }
                else {
                    result += 45;
                }
                logger.debug("Enemy near you!");
            }
        }
        if(isIn(x, y+1) && field[x][y+1].getColor() == enemyColor) {
            int dames = field[x][y+1].getGroup().getDamesCount();
            if(dames <= 2) {
                if(dames == 1) {
                    result += 100;
                }
                else {
                    result += 45;
                }
                logger.debug("Enemy near you!");
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
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                Stone stone = field[i][j];
                newField[i][j] = new Stone(stone.getColor(),
                        stone.getRowNumber(), stone.getColumnNumber());
            }
        }
        return newField;
    }

    public List<GameAction> getPossibleActions(Stone[][] field){
        List<GameAction> possibleActions = new ArrayList<>();
        Validation validation = new Validation(board);
        int n = field.length;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(field[i][j].getColor() == Color.EMPTY &&
                        validation.isCorrectMove(field[i][j].getColor(), field[i][j].getRowNumber(), field[i][j].getColumnNumber())){
                    possibleActions.add(new GameAction(RequestType.TURN, i, j));
                }
            }
        }
        return possibleActions;
    }

    List<Stone[][]> getCopyOfGameHistory(List<Stone[][]> gameHistory){
        List<Stone[][]> newGameHistory = new ArrayList<>();
        int n = gameHistory.size();
        for(int i = 0; i < n; i++){
            newGameHistory.add(getCopyOfField(gameHistory.get(i)));
        }
        return newGameHistory;
    }

    private GameAction skipTurn() {
        return new GameAction(RequestType.PASS, 0, 0);
    }

    public void setGameField(final Stone[][] gameField) {
        this.gameField = gameField;
    }
}
