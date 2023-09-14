package io.deeplay.intership.game;

import io.deeplay.intership.exception.ServerErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.exception.game.GameException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Player;
import io.deeplay.intership.model.Score;
import io.deeplay.intership.model.Stone;

/**
 * Класс для контроля игровой сессии (процесса).
 * Имеются два игрока (белый, черный), создатель игры, сама игра, номер игры,
 * флаг начала игры и ссылка на текущего игрока.
 */
public class GameSession {
    private final String gameId;
    private final Game game;
    private boolean isStarted;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentTurn;
    private int nextPlayer = 0;

    public GameSession(final String gameId) {
        this.gameId = gameId;
        this.game = new Game();
        this.isStarted = false;
    }

    public String getGameId() {
        return gameId;
    }

    public Stone[][] getGameField() {
        return game.getGameField();
    }

    public void startGame() {
        currentTurn = blackPlayer;
        isStarted = true;
    }

    public boolean isFinished() {
        return game.gameIsOver();
    }

    public int getNextPlayer() {
        return (nextPlayer) % 2;
    }

    /**
     * Доавляем присоединившегося игрока в игру, а также назначаем цвета
     * создателю игры и второму игроку.
     * Проверяем, что логины разные у игроков, чтобы нельзя было с самим собой.
     *
     * @param player {@link Player} второй игрок
     * @throws ServerException если игрок пытается сам с собой сыграть
     */
    public synchronized void addPlayer(final Player player) throws ServerException {
        setColor(player);
    }

    /**
     * Устанавливаем цвет по принципу: хозяин не выбрал цвет, тогда второй игрок
     * получает тот цвет, какой выбрал, если же хозяин выбрал цвет, то тогда
     * второй игрок получает оставшийся цвет.
     * Если оба не выбрали, то хозяин черный, а холоп белый.
     *
     * @param player {@link Player} второй игрок, creator уже определен
     */
    public void setColor(final Player player) throws ServerException {
        switch (Color.valueOf(player.color())) {
            case BLACK -> setBlackPlayer(player);
            case WHITE -> setWhitePlayer(player);
            default -> throw new ServerException(ServerErrorCode.INVALID_COLOR);
        }
    }

    public void setBlackPlayer(final Player player) throws ServerException {
        if (blackPlayer != null) {
            throw new ServerException(ServerErrorCode.INVALID_COLOR);
        }
        blackPlayer = player;
    }

    public void setWhitePlayer(final Player player) throws ServerException {
        if (whitePlayer != null) {
            throw new ServerException(ServerErrorCode.INVALID_COLOR);
        }
        whitePlayer = player;
    }

    /**
     * Позволяет сделать ход игрока в GameService, осуществляет проверку,
     * что игра уже начата, но еще не закончена, также следит за очередностью
     * хода и меняет очередность при успешной попытке.
     *
     * @param login логин игрока, который сейчас делает ход
     * @param stone {@link Stone} определяет куда ходит игрок, на какую позицию
     * @return {@link Stone} массив-копия доски для безопасного изменения состояния
     * @throws ServerException при некорректной попытке сделать ход
     */
    public synchronized Stone[][] turn(final String login, final Stone stone) throws ServerException {
        isNotStarted();
        isFinishedGame();
        checkTurnOrder(login);
        try {
            Stone[][] gameField = game.makeMove(stone);
            changePlayerTurn();
            nextPlayer += 1;
            return getFieldCopy(gameField);
        } catch (GameException ex) {
            throw new ServerException(ServerErrorCode.INVALID_MOVE);
        }
    }

    /**
     * Позволяет пропустить ход, также проверяется, что игра начата, но
     * не окончена. Пропускается ход в Game, проверяется и изменяется
     * очередность хода. Данный метод также предназначен для управления
     * процессом в GameService.
     *
     * @param login игрок пропускающий ход
     * @return {@link Stone} массив-копия состояния доски после пропуска
     * @throws {@link ServerException} любые ошибки с сервером
     */
    public synchronized Stone[][] pass(final String login) throws ServerException {
        isNotStarted();
        isFinishedGame();
        final Player player = checkTurnOrder(login);

        try {
            Stone[][] gameField = game.skipTurn(Color.valueOf(player.color()));
            changePlayerTurn();
            nextPlayer += 1;
            return getFieldCopy(gameField);
        } catch (GameException e) {
            throw new ServerException(ServerErrorCode.GAME_WAS_FINISHED);
        }

    }

    public Score getGameScore() {
        return game.getGameScore();
    }

    /**
     * Используем для корректного выполнения игровых действий, можем делать
     * ходы, пасовать только при условии, что игра начата.
     *
     * @throws ServerException если игра еще не началась
     */
    private synchronized void isNotStarted() throws ServerException {
        if (!isStarted) {
            throw new ServerException(ServerErrorCode.GAME_NOT_STARTED);
        }
    }

    /**
     * Используем для контроля очередности ходов игроков во время выполнения
     * очередного хода в функции turn.
     *
     * @param login логин игрока, желающего сделать ход
     * @throws ServerException при попытке сходить не в свою очередь
     */
    private synchronized Player checkTurnOrder(final String login) throws ServerException {
        if (!currentTurn.name().equals(login)) {
            throw new ServerException(ServerErrorCode.INVALID_TURN_ORDER);
        }
        return currentTurn;
    }

    /**
     * Используем для передачи очередности хода в функции turn.
     */
    private synchronized void changePlayerTurn() {
        if (blackPlayer == currentTurn) {
            currentTurn = whitePlayer;
        } else {
            currentTurn = blackPlayer;
        }
    }

    /**
     * Используем для контроля конца игры, когда делаем очередной ход нужно
     * убедиться, что игра еще не закончена.
     *
     * @throws ServerException если игра уже закончена
     */
    private synchronized void isFinishedGame() throws ServerException {
        if (game.gameIsOver()) {
            throw new ServerException(ServerErrorCode.GAME_WAS_FINISHED);
        }
    }

    /**
     * Берем копию доски, чтобы в дальнейшем не менялось состояние например, если
     * неправильно походили, то это не отразится на доске.
     *
     * @param gameField {@link Stone} массив текущего состояние доски
     * @return {@link Stone} массив-копия текущего состояния доски
     */
    private Stone[][] getFieldCopy(final Stone[][] gameField) {
        Stone[][] newField = new Stone[gameField.length][gameField.length];
        for (int i = 0; i < newField.length; i++) {
            for (int j = 0; j < newField[i].length; j++) {
                newField[i][j] = new Stone(
                        gameField[i][j].getColor(),
                        gameField[i][j].getRowNumber(),
                        gameField[i][j].getColumnNumber(),
                        null);
            }
        }
        return newField;
    }
}