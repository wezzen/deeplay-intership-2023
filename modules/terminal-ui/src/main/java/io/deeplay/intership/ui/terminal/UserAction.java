package io.deeplay.intership.ui.terminal;

/**
 * Перечисление UserAction представляет различные действия, которые пользователь может выполнять в
 * игре. У каждого действия есть связанный с ним целочисленный код, который можно использовать для
 * целей идентификации.
 */
public enum UserAction {
  START_GAME(1),
  SKIP(2),
  MOVE(3),
  CHOOSE_COLOR(4),
  END_GAME(5),
  CHOOSE_WHITE_COLOR(6),
  CHOOSE_BLACK_COLOR(7),
  CHOOSE_EMPTY_COLOR(8),
  ;

  private final int actionCode;

  /**
   * Создает перечисление UserAction с указанным кодом действия.
   *
   * @param actionCode код действия, связанный с действием пользователя
   */
  UserAction(int actionCode) {
    this.actionCode = actionCode;
  }

  /**
   * Получает код действия, связанный с действием пользователя.
   *
   * @return код действия
   */
  public int getActionCode() {
    return actionCode;
  }
}
