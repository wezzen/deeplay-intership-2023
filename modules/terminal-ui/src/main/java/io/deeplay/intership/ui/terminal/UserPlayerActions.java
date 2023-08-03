package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.service.Board;
import io.deeplay.intership.service.Color;
import io.deeplay.intership.service.Stone;

/**
 * Класс {@link UserPlayerActions} реализует интерфейс {@link PlayerActions}, с которым
 * взаимодействует пользователь через консоль, чтобы играть.
 */
public class UserPlayerActions implements PlayerActions {

    private final Color color;
    private final Display display;
    private final InputUtil inputUtil;
    private final Converter converter;

    /**
     * Конструктор класса UserPlayerActions.
     *
     * @param color цвет игрока
     */
    public UserPlayerActions(Color color) {
        this.color = color;
        this.display = new Display();
        this.inputUtil = new InputUtil();
        this.converter = new Converter();
    }


    /**
     * Выбирает одно из двух действий на основе ввода пользователя: пропустить ход или поставить
     * камень.
     *
     * @param board класс {@link Board} хранящий состояние доски.
     * @return камень {@link Stone} содержащий цвет игрока и координаты заданные пользователем.
     */
    @Override
    public Stone chooseGameAction(final Board board) {
        display.showBoard(board);
        display.showGameActions();
        return switch (inputUtil.inputAction()) {
            case MOVE -> makeMove(board);
            case SKIP -> skipTurn();
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * Делает ход в игре на основе ввода пользователя.
     *
     * @param board класс {@link Board} хранящий состояние доски.
     * @return камень {@link Stone} содержащий цвет игрока и координаты заданные пользователем.
     */
    @Override
    public Stone makeMove(final Board board) {
        display.showBoard(board);
        display.showMoveRules();
        return inputUtil.inputMove(color);
    }

    /**
     * Реализует пропуск хода игрока.
     */
    @Override
    public Stone skipTurn() {
        display.showAwaitState();
        return new Stone(Color.EMPTY, 0, 0);
    }

    /**
     * Реализует выбор цвета игрока.
     *
     * @return цвет {@link Color}, за который будет играть пользователь
     */
    @Override
    public Color chooseColor() {
        display.showColorSelection();
        return converter.convertActionToColor(inputUtil.inputColorAction());
    }


    /**
     * Реализует окончание игры.
     * @param gameResult строка с результатами игры
     */
    @Override
    public void endGame(String gameResult) {
        display.showGameResult(gameResult);
    }
}
