package io.deeplay.inership.server;

/**
 * Класс отвечает за вывод в лог на консоль состояния поля на доске {@link Board}
 */

public class BoardLogger {


  /**
   * Выводит на консоль текущее состояние доски {@link Board}
   *
   * @param board {@link Board} из доски выводится состояние игрового поля {@code gameField}
   */
  public void write(final Board board) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < board.getField().length; i++) {
      for (int j = 0; j < board.getField()[i].length; j++) {
        stringBuilder.append(getCellSymbol(board.getField()[i][j]));
      }
      if (i != (board.getField().length - 1)) {
        stringBuilder.append("\n");
      }
    }
    System.out.println(stringBuilder);
  }


  /**
   * Выводит на консоль текущее состояние доски {@link Board}
   *
   * @param stone камень, который, нужно отобразить в выводе консоли{@link Stone}
   * @return символ соответствующий цвету камня{@link Stone}
   */
  public char getCellSymbol(final Stone stone) {
    return switch (stone.getColor()) {
      case BLACK -> '-';
      case WHITE -> '+';
      default -> '0';
    };
  }
}
