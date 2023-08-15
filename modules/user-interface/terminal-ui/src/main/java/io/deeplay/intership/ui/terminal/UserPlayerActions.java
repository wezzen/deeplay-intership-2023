package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.model.Move;
import io.deeplay.intership.action.PlayerActions;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Stone;

/**
 * Класс {@link UserPlayerActions} реализует интерфейс {@link PlayerActions}, с которым
 * взаимодействует пользователь через консоль, чтобы играть.
 */
public class UserPlayerActions implements PlayerActions {
    private final Display display;
    private final InputUtil inputUtil;
    private final Converter converter;
    private String token;
    private Color color;

    /**
     * Конструктор класса UserPlayerActions.
     */
    public UserPlayerActions() {
        this.display = new Display();
        this.inputUtil = new InputUtil();
        this.converter = new Converter();
    }


    /**
     * Выбирает одно из двух действий на основе ввода пользователя: пропустить ход или поставить
     * камень.
     *
     * @param gameField массив {@link Stone} хранящий состояние доски.
     * @return ход {@link Move} содержащий токен, цвет игрока и координаты заданные пользователем.
     */
    @Override
    public Move chooseGameAction(final Stone[][] gameField) {
        display.showBoard(gameField);
        display.showGameActions();
        return switch (inputUtil.inputAction()) {
            case MOVE -> makeMove(gameField);
            case SKIP -> skipTurn();
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * Делает ход в игре на основе ввода пользователя.
     *
     * @param gameField массив {@link Stone} хранящий состояние доски.
     * @return ход {@link Move} содержащий цвет игрока и координаты заданные пользователем.
     */
    @Override
    public Move makeMove(final Stone[][] gameField) {
        display.showBoard(gameField);
        display.showMoveRules();
        Stone stone = inputUtil.inputMove(color);
        return new Move(
                token,
                stone.getColor().name(),
                stone.getRowNumber(),
                stone.getColumnNumber()
        );
    }

    /**
     * Реализует пропуск хода игрока.
     */
    @Override
    public Move skipTurn() {
        display.showAwaitState();
        return new Move(
                token,
                Color.EMPTY.name(),
                0,
                0);
    }

    /**
     * Реализует выбор цвета игрока.
     *
     * @return цвет {@link Color}, за который будет играть пользователь
     */
    @Override
    public Color chooseColor() {
        display.showColorSelection();
        color = converter.convertActionToColor(inputUtil.inputColorAction());
        return color;
    }


    /**
     * Реализует окончание игры.
     *
     * @param gameResult строка с результатами игры
     */
    @Override
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
