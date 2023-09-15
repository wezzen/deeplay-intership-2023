package io.deeplay.intership.minimax.bot.gleb;

import io.deeplay.intership.bot.random.Bot;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.exception.ClientErrorCode;
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

public class MinMaxBot extends Bot {

    private final CheckGameOver gameCheckOver;
    private Board board;
    private Stone[][] gameField;
    private Display display = new Display();
    private int action;
    private int rowTurn;
    private int columnTurn;

    public record TurnAndEstimation(GameAction gameAction, Double utility) {}

    public MinMaxBot(String name, Color color) {
        super(name, color);

        board = new Board();
        gameField = board.getField();
        gameCheckOver = new CheckGameOver();
        action = 1;
    }

    public GameAction makeMove(Stone[][] field) throws ClientException {
        /*if(!gameCheckOver.canMakeMove(color)){
            System.out.println("*******");
            throw new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);
        }*/

        display.showBoard(field);
        List<Stone[][]> history = new ArrayList<>();
        history.add(getCopyOfField(field));

        var turnAndEstimation = executeMinMax(4, Double.MIN_VALUE,
                Double.MAX_VALUE, getCopyOfField(field), color, color, null,
                getPossibleActions(field), history);
        this.rowTurn = turnAndEstimation.gameAction.row();
        this.columnTurn = turnAndEstimation.gameAction.column();
        System.out.println(turnAndEstimation.gameAction.row() + " " + turnAndEstimation.gameAction.column());
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
                System.out.println("Depth = " + depth);
                GameAction action = new GameAction(RequestType.PASS, 0, 0);
                return new TurnAndEstimation(action, getSimpleEstimation(field, agentColor));
            }
            return new TurnAndEstimation(lastCell,
                    getSimpleEstimation(field, agentColor));
        }

        var isMax = currentColor == agentColor;
        //System.out.println("isMax = " + isMax);
        var turnAndEstimation = new TurnAndEstimation(null, isMax ? Double.MIN_VALUE : Double.MAX_VALUE);

        if(possibleActions.isEmpty()) {
            System.out.println("EMPTY!!!!");
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
                    turnAndEstimation = new TurnAndEstimation(action, value.utility);
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

    public double getSimpleEstimation(Stone[][] field, Color agentColor){
        ScoreCalculator scoreCalculator = new ScoreCalculator(field);
        Score score = scoreCalculator.getTotalScore();
        return (switch(agentColor){
            case BLACK -> score.blackPoints() - score.whitePoints();
            case WHITE -> score.whitePoints() - score.blackPoints();
            default -> throw new IllegalStateException("Unexpected value: " + agentColor);
        });
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
