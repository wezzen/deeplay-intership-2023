package io.deeplay.intership.action;


import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Move;
import io.deeplay.intership.model.Stone;

/**
 * интерфейс PlayerActions определяет действия игрока в игре.
 * игрок может сделать ход, пропустить свой ход, выбрать цвет, начать игру и завершить игру.
 */
public interface PlayerActions {

    Move chooseGameAction(final Stone[][] gameField);
    /**
     * Определяет действие игрока "сделать ход".
     */
    Move makeMove(final Stone[][] gameField);

    /**
     * Определяет действие игрока "пропустить ход".
     */
    Move skipTurn();

    /**
     * Определяет действие игрока "выбрать цвет".
     */
    Color chooseColor();


    /**
     * Определяет действие игрока "завершить игру".
     */
    void endGame(String gameResult);
}

