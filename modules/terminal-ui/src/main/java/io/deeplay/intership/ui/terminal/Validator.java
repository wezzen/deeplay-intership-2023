package io.deeplay.intership.ui.terminal;

/**
 * Класс Validator предоставляет методы для проверки чисел в указанном диапазоне.
 */
public class Validator {

  private static final int DEFAULT_MIN_BORDER_VALUE = 1;
  private static final int DEFAULT_MAX_BORDER_VALUE = 9;
  private final int minBorderValue;
  private final int maxBorderValue;


  /**
   * Создает новый объект Validator с указанными минимальными и максимальными значениями границ.
   *
   * @param minBorderValue минимально допустимое значение в диапазоне
   * @param maxBorderValue максимально допустимое значение в диапазоне
   */
  public Validator(int minBorderValue, int maxBorderValue) {
    this.minBorderValue = minBorderValue;
    this.maxBorderValue = maxBorderValue;
  }

  /**
   * Создает новый объект {@code Validator} с минимальными и максимальными значениями границ по
   * умолчанию. Диапазон по умолчанию от 1 до 9.
   */
  public Validator() {
    this(DEFAULT_MIN_BORDER_VALUE, DEFAULT_MAX_BORDER_VALUE);
  }

  /**
   * Проверяет, находится ли заданное число в допустимом диапазоне.
   *
   * @param number число для проверки
   * @return {@code true}, если число находится в допустимом диапазоне, иначе {@code false}
   */
  public boolean isValidNumber(int number) {
    return number >= minBorderValue &&
        number <= maxBorderValue;
  }

  /**
   * Проверяет, представляет ли данный ввод допустимое действие.
   *
   * @param input входная строка для проверки
   * @return {@code true}, если ввод представляет допустимое действие (имеет длину в 1 символ), в
   * противном случае {@code false}
   */
  public boolean isValidAction(String input) {
    return input.trim().length() == 1;
  }

  public boolean isColorAction(UserAction action) {
    return action == UserAction.CHOOSE_WHITE_COLOR ||
        action == UserAction.CHOOSE_BLACK_COLOR ||
        action == UserAction.CHOOSE_EMPTY_COLOR;
  }
}
