package io.deeplay.intership.simons.bot;

import io.deeplay.intership.bot.random.RandomBot;
import io.deeplay.intership.decision.maker.GameAction;
import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.exception.game.GameException;
import io.deeplay.intership.game.Game;
import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;

import java.util.ArrayList;
import java.util.List;

public class SimonsBot extends RandomBot {
    private Stone[][] gameField = new Board().getField();
    private Tree gameTree;

    public SimonsBot(String name, Color color) {
        super(name, color);
    }

    @Override
    public GameAction getGameAction() {
        //TODO: прикрутить валидацию на бота
        if (canMove()) {
            return makeMove(gameField);
        } else {
            return skipTurn();
        }
    }

    @Override
    public Color getColor() {
        return color;
    }

    private GameAction makeMove(final Stone[][] gameField) {
        List<Stone> emptyStones = getEmptyStones(gameField);
        Stone stone = emptyStones.get((int) (Math.random() * emptyStones.size()));
        buildTree(gameField, 3);
        stone = getMoveFromTree();
        return new GameAction(
                RequestType.TURN,
                stone.getRowNumber(),
                stone.getColumnNumber()
        );

    }

    private Stone getMoveFromTree() {
        Node resultNode = null;
        double maxScore = -100;
        for (Node node : gameTree.top.children) {
            double score = getScoreFromNode(node);
            if (score > maxScore) {
                maxScore = score;
                resultNode = node;
            }
        }
        return resultNode.stone;
    }

    private double getScoreFromNode(Node node) {
        if (node.depth < gameTree.depth) {
            if (node.isMax) {
                double maxScore = -100;
                for (Node childNode : node.children) {
                    if (childNode.score > maxScore) {
                        maxScore = getScoreFromNode(childNode);
                    }
                }
                return maxScore;
            } else {
                double minScore = node.children.get(1).score;
                for (Node childNode : node.children) {
                    if (childNode.score < minScore) {
                        minScore = getScoreFromNode(childNode);
                    }
                }
                return minScore;
            }
        } else {
            return node.score;
        }
    }

    private boolean canMove() {
        boolean canMove = false;
        for (int i = 0; i < gameField.length && !canMove; i++) {
            for (int j = 0; j < gameField[i].length && !canMove; j++) {
                Stone stone = gameField[i][j];
                if (stone.getColor() == Color.EMPTY) {
                    canMove = true;
                }
            }
        }
        return canMove;
    }

    private void buildTree(Stone[][] field, int maxDepth) {
        gameTree = new Tree();
        gameTree.depth = maxDepth;
        Game game = new Game();
        changeField(game.getGameField(), field);
        gameTree.top = buildNode(1, gameTree.depth, game);

    }

    private Node buildNode(int depth, int maxdepth, Game game) {
        boolean isMax = (depth % 2) == 1;
        if (depth <= maxdepth) {
            Node node = new Node(isMax, depth);
            Stone[][] parentField = game.getGameField();
            List<Stone> emptyStones = getEmptyStones(parentField);
            for (Stone emptyStone : emptyStones) {
                try {
                    Game newGame = new Game();
                    changeField(newGame.getGameField(), parentField);
                    Stone move = getColorizedStone(isMax, emptyStone);
                    newGame.makeMove(move);
                    Node leafNode = buildNode(depth + 1, maxdepth, newGame);
                    leafNode.stone = move;
                    node.children.add(leafNode);
                } catch (GameException e) {
                    e.printStackTrace();
                }
            }
            node.nodeGame = game;
            node.score = getNodeScore(node);
            return node;
        } else {
            return new Node(isMax, depth);
        }
    }

    private double getNodeScore(Node node) {
        double answer = 0;
        if ((color.equals(Color.BLACK) && node.isMax) ||
                (color.equals(Color.WHITE) && !node.isMax)) {
            answer = node.nodeGame.getGameScore().blackPoints();
        } else {
            answer = node.nodeGame.getGameScore().whitePoints();
        }
        return answer;
    }

    private Stone getColorizedStone(boolean isMax, Stone emptyStone) {
        Stone move;
        if (isMax) {
            move = new Stone(color, emptyStone.getRowNumber(), emptyStone.getColumnNumber());
        } else {
            if (color == Color.BLACK) {
                move = new Stone(Color.WHITE, emptyStone.getRowNumber(), emptyStone.getColumnNumber());
            } else {
                move = new Stone(Color.BLACK, emptyStone.getRowNumber(), emptyStone.getColumnNumber());
            }
        }
        return move;
    }

    private List<Stone> getEmptyStones(final Stone[][] gameField) {
        List<Stone> emptyStones = new ArrayList<>();
        Stone[][] field = gameField;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                Stone stone = field[i][j];
                if (stone.getColor() == Color.EMPTY) {
                    emptyStones.add(stone);
                }
            }
        }
        return emptyStones;
    }

    private void changeField(Stone[][] fieldTo, Stone[][] fieldFrom) {
        for (int i = 0; i < fieldTo.length; i++) {
            for (int j = 0; j < fieldTo.length; j++) {
                fieldTo[i][j].setColor(fieldFrom[i][j].getColor());
            }
        }
    }
    @Override
    public void startGame() {

    }

    @Override
    public void endGame() {

    }

    private GameAction skipTurn() {
        return new GameAction(RequestType.PASS, 0, 0);
    }

    public void setGameField(final Stone[][] gameField) {
        this.gameField = gameField;
    }
}

