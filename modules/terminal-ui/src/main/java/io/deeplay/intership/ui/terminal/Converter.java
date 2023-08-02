package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.service.Color;
import io.deeplay.intership.service.Stone;
import java.util.Arrays;


/**
 * Класс Converter предоставляет методы для анализа входных строк и преобразования представлений
 * символов. к соответствующим целочисленным значениям для игры в го.
 */
public class Converter {

  /**
   * Анализирует ввод строки и конвертирует в объект {@link Stone}.
   *
   * @param input входная строку для валидации и парсинга в {@link Stone}
   * @return готовый объект {@link Stone} на основе анализа
   * @throws IllegalArgumentException, если ввод не в правильном формате
   */
  public Stone convertStringToStone(String input, Color color) {
    String[] parse =
        Arrays.stream(input.trim().split(" "))
            .filter(str -> !str.isEmpty())
            .toArray(String[]::new);
    if (Arrays.stream(parse).count() != 2) {
      throw new IllegalArgumentException();
    }

    return new Stone(
        color,
        convertCharToInt(parse[0]) - 1,
        convertCharToInt(parse[1]) - 1
    );
  }

  /**
   * Преобразует символьное представление столбца на доске в соответствующее целочисленное значение.
   * Допустимые символы для обозначения строки от 1 до 19. Координаты столбцов от  «A» до «T»,
   * представляющие столбцы с 1 по 19 соответственно.
   *
   * @param str символьное представление столбца
   * @return соответствующее целочисленное значение столбца
   * @throws IllegalArgumentException, если введенный символ недействителен
   */
  public int convertCharToInt(String str) {
    return switch (str.toUpperCase()) {
      case "A", "1" -> 1;
      case "B", "2" -> 2;
      case "C", "3" -> 3;
      case "D", "4" -> 4;
      case "E", "5" -> 5;
      case "F", "6" -> 6;
      case "G", "7" -> 7;
      case "H", "8" -> 8;
      case "J", "9" -> 9;
      case "K", "10" -> 10;
      case "L", "11" -> 11;
      case "M", "12" -> 12;
      case "N", "13" -> 13;
      case "O", "14" -> 14;
      case "P", "15" -> 15;
      case "Q", "16" -> 16;
      case "R", "17" -> 17;
      case "S", "18" -> 18;
      case "T", "19" -> 19;
      default -> throw new IllegalArgumentException();
    };
  }


  /**
   * Преобразует числовое представление столбца в соответствующее символьное значение на доске.
   * Допустимые символы для обозначения строки от 1 до 19. Координаты столбцов от  «A» до «T»,
   * представляющие столбцы с 1 по 19 соответственно.
   *
   * @param number числовое представление столбца
   * @return соответствующее символьное значение столбца
   * @throws IllegalArgumentException, если введенный символ недействителен
   */

  public char convertIntToChar(int number) {
    return switch (number) {
      case 1 -> 'A';
      case 2 -> 'B';
      case 3 -> 'C';
      case 4 -> 'D';
      case 5 -> 'E';
      case 6 -> 'F';
      case 7 -> 'G';
      case 8 -> 'H';
      case 9 -> 'J';
      case 10 -> 'K';
      case 11 -> 'L';
      case 12 -> 'M';
      case 13 -> 'N';
      case 14 -> 'O';
      case 15 -> 'P';
      case 16 -> 'Q';
      case 17 -> 'R';
      case 18 -> 'S';
      case 19 -> 'T';
      default -> throw new IllegalArgumentException();
    };
  }


  /**
   * Сопоставляет цвету камня, его символьно изображение.
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
