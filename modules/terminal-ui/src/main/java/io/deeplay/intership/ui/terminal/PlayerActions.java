package io.deeplay.intership.ui.terminal;


import io.deeplay.intership.service.Board;
import io.deeplay.intership.service.Color;
import io.deeplay.intership.service.Stone;

/**
 * Интерфейс PlayerActions определяет действия игрока в игре.
 * <p>
 * Игрок может сделать ход, пропустить свой ход, выбрать цвет,
 * <p>
 * начать игру и завершить игру.
 */
public interface PlayerActions {

    Stone chooseGameAction(final Board board);

    /**
     * Определяет действие игрока "сделать ход".
     */
    Stone makeMove(final Board board);

    /**
     * Определяет действие игрока "пропустить ход".
     */
    Stone skipTurn();

    /**
     * Определяет действие игрока "выбрать цвет".
     */
    Color chooseColor();

    /**
     * Определяет действие игрока "начать игру".
     */
    void startGame();

    /**
     * Определяет действие игрока "завершить игру".
     */
    void endGame();
}

