package io.deeplay.intership.game;

import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.exception.game.GameException;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Player;
import io.deeplay.intership.model.Stone;

public class GameSession {
    private final String gameId;
    private final Game game;
    private boolean isStarted;
    private Player creator;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player currentTurn;

    public GameSession(final String gameId) {
        this.gameId = gameId;
        this.game = new Game();
        this.isStarted = false;
    }

    public String getGameId() {
        return gameId;
    }

    /**
     * Добавляем создателя игры, чтобы понимать, кто является творцом.
     * Это позволит контролировать ситуации с подключеним к игре.
     * @param player один из игроков
     */
    public void addCreator(final Player player) {
        creator = player;
    }

    /**
     * Доавляем присоединившегося игрока в игру, а также назначаем цвета
     * создателю игры и второму игроку.
     * Проверяем, что логины разные у игроков, чтобы нельзя было с самим собой.
     * @param player второй игрок
     * @throws ServerException ошибки с сервером
     */
    public synchronized void addPlayer(final Player player) throws ServerException {
        if (player.login().equals(creator.login())) {
            throw new ServerException(ErrorCode.REPEATED_PLAYER);
        }

        setColor(player);
        currentTurn = blackPlayer;
        isStarted = true;
    }

    /**
     * Устанавливаем цвет по принципу: хозяин не выбрал цвет, тогда второй игрок
     * получает тот цвет, какой выбрал, если же хозяин выбрал цвет, то тогда
     * второй игрок получает оставшийся цвет.
     * Если оба не выбрали, то хозяин черный, а холоп белый.
     * @param player второй игрок, creator уже определен к этому моменту
     */
    public void setColor(final Player player) {
        if(Color.EMPTY.name().equals(creator.color())) {
            if(Color.BLACK.name().equals(player.color())) {
                blackPlayer = player;
            }
            else if(Color.WHITE.name().equals(player.color())){
                whitePlayer = player;
            }
            else {
                blackPlayer = creator;
                whitePlayer = player;
            }
        }
        else if(Color.BLACK.name().equals(creator.color())) {
            blackPlayer = creator;
            whitePlayer = player;
        }
        else {
            blackPlayer = player;
            whitePlayer = creator;
        }
    }

    /**
     * Позволяет сделать ход игрока в GameService, осуществляет проверку,
     * что игра уже начата, но еще не закончена, также следит за очередностью
     * хода и меняет очередность при успешной попытке.
     * @param player определяет какой игрок сейчас делает ход
     * @param stone определяет куда ходит игрок, на какую позицию
     * @return {@link Stone[][]} копия доски для безопасного изменения состояния.
     * @throws ServerException бросается при проблемах с сервером
     */
    public synchronized Stone[][] turn(final Player player, final Stone stone) throws ServerException {
        isNotStarted();
        isFinishedGame();
        checkTurnOrder(player);
        try {
            Stone[][] gameField = game.makeMove(stone);
            changePlayerTurn();
            return getFieldCopy(gameField);
        } catch (GameException ex) {
            throw new ServerException(ErrorCode.INVALID_MOVE);
        }
    }

    /**
     * Позволяет пропустить ход, также проверяется, что игра начата, но
     * не окончена. Пропускается ход в Game, проверяется и изменяется
     * очередность хода. Данный метод также предназначен для управления
     * процессом в GameService.
     * @param player
     * @return
     * @throws ServerException
     */
    public synchronized Stone[][] pass(final Player player) throws ServerException {
        isNotStarted();
        isFinishedGame();
        checkTurnOrder(player);

        Stone[][] gameField = game.skipTurn(Color.valueOf(player.color()));
        changePlayerTurn();
        return getFieldCopy(gameField);
    }

    /**
     * Используем для корректного выполнения игровых действий, можем делать
     * ходы, пасовать только при условии, что игра начата.
     * @throws ServerException в случае проблем с сервером
     */
    private synchronized void isNotStarted() throws ServerException {
        if (!isStarted) {
            throw new ServerException(ErrorCode.GAME_NOT_STARTED);
        }
    }

    /**
     * Используем для контроля очередности ходов игроков во время выполнения
     * очередного хода в функции turn.
     * @param player игрок, желающий сделать ход
     * @throws ServerException кидаем при ошибках сервера
     */
    private synchronized void checkTurnOrder(final Player player) throws ServerException {
        if (!currentTurn.login().equals(player.login())) {
            throw new ServerException(ErrorCode.INVALID_TURN_ORDER);
        }
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
     * @throws ServerException ошибки с сервером
     */
    private synchronized void isFinishedGame() throws ServerException {
        if (game.gameIsOver()) {
            throw new ServerException(ErrorCode.GAME_WAS_FINISHED);
        }
    }

    /**
     * Берем копию доски, чтобы в дальнейшем не менялось состояние например, если
     * неправильно походили, то это не отразится на доске.
     * @param gameField текущее состояние доски
     * @return {@link Stone[][]} копия текущего состояния доски
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