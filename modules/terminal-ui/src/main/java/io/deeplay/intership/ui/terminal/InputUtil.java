package io.deeplay.intership.ui.terminal;

import io.deeplay.intership.service.Color;
import io.deeplay.intership.service.Stone;
import java.util.Scanner;

/**
 * Класс InputUtil предоставляет метод для чтения пользовательского ввода и преобразования его в
 * объект {@link Stone}.
 */
public class InputUtil {

  private final Scanner scanner;
  private final Converter converter;

  /**
   * Создает новый объект InputUtil с {@link Converter} по умолчанию.
   */
  public InputUtil() {
    this.scanner = new Scanner(System.in);
    this.converter = new Converter();
  }

  /**
   * Читает пользовательский ввод и преобразует его в объект {@link Stone}.
   *
   * @return созданный объект {@link Stone} с координатами на основе пользовательского ввода
   * @throws IllegalArgumentException, если ввод не в правильном формате
   */
  public Stone makeInput(Color color) {
    String input = scanner.nextLine();

    return converter.convertStringToStone(input, color);
  }

}
