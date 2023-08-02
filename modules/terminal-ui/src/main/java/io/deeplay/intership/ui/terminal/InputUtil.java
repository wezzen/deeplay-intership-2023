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
  private final Validator validator;

  /**
   * Создает новый объект InputUtil с {@link Converter} по умолчанию.
   */
  public InputUtil() {
    this.scanner = new Scanner(System.in);
    this.converter = new Converter();
    this.validator = new Validator();
  }

  /**
   * Читает пользовательский ввод и преобразует его в объект {@link Stone}.
   *
   * @return созданный объект {@link Stone} с координатами на основе пользовательского ввода
   * @throws IllegalArgumentException, если ввод не в правильном формате
   */
  public Stone inputMove(Color color) {
    String input = scanner.nextLine();
    return converter.convertStringToStone(input, color);
  }

  /**
   * Читает пользовательский ввод и преобразует его в объект {@link UserAction}.
   *
   * @return объект {@link UserAction} на основе пользовательского ввода
   */
  public UserAction inputAction() {
    String input;
    do {
      input = scanner.nextLine();
    } while (validator.isValidAction(input));

    return converter.convertStringToAction(input);
  }

  /**
   * Читает пользовательский ввод и преобразует его в объект {@link UserAction}, пока пользователь
   * не введет действие, отвечающее за выбор цвета.
   *
   * @return объект {@link UserAction} на основе пользовательского ввода
   */
  public UserAction inputColorAction() {
    UserAction action;
    do {
      action = inputAction();
    } while (validator.isColorAction(action));
    return action;
  }
}
