package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.service.Color;

/**
 * Класс реализует интерфейс {@link PlayerActions}, с которым взаимодействует пользователь через
 * консоль, чтобы играть.
 */
public class UserPlayerActions implements PlayerActions {

  private final Color color;
  private final InputUtil inputUtil;

  /**
   * Конструктор класса UserPlayerActions.
   *
   * @param color цвет игрока
   */
  public UserPlayerActions(Color color) {
    this.color = color;
    this.inputUtil = new InputUtil();
  }


  /**
   * Делает ход в игре на основе ввода пользователя.
   */
  @Override
  public void makeMove() {

  }

  /**
   * Реализует пропуск хода игрока.
   */
  @Override
  public void skipTurn() {

  }

  /**
   * Реализует выбор цвета игрока.
   */
  @Override
  public void chooseColor() {

  }

  /**
   * Реализует начало игры.
   */
  @Override
  public void startGame() {

  }

  /**
   * Реализует окончание игры.
   */
  @Override
  public void endGame() {

  }
}
