package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.dto.request.BaseDto;
import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.model.Move;
import io.deeplay.intership.action.PlayerActions;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс {@link UserPlayerActions} реализует интерфейс {@link PlayerActions}, с которым
 * взаимодействует пользователь через консоль, чтобы играть.
 */
public class UserPlayerActions {
    private final Display display;
    private String token;
    private Color color;
    /**
     * Конструктор класса UserPlayerActions.
     */
    public UserPlayerActions() {
        this.display = new Display();
    }


    /**
     * Выбирает одно из двух действий на основе ввода пользователя: пропустить ход или поставить
     * камень.
     *
     * @param gameField массив {@link Stone} хранящий состояние доски.
     * @return ход {@link Move} содержащий токен, цвет игрока и координаты заданные пользователем.
     */
    public void chooseGameAction(final Stone[][] gameField) {
    }

    public void enterGame() {
    }
    public void chooseGameRoom() {
    }
    public void joinGame(){
    }
    public void registerPlayer(){
    }
    public void loginPlayer(){
    }
    /**
     * Делает ход в игре на основе ввода пользователя.
     *
     * @param gameField массив {@link Stone} хранящий состояние доски.
     * @return ход {@link Move} содержащий цвет игрока и координаты заданные пользователем.
     */
    public void makeMove(final Stone[][] gameField) {
    }

    /**
     * Реализует пропуск хода игрока.
     */
    public void skipTurn() {
        display.showAwaitState();
    }



    /**
     * Реализует окончание игры.
     *
     * @param gameResult строка с результатами игры
     */
    public void endGame(String gameResult) {
        display.showGameResult(gameResult);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
