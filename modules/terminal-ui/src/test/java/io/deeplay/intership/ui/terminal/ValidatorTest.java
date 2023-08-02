package io.deeplay.intership.ui.terminal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ValidatorTest {

  @Test
  public void testCorrectIsValidNumber1() {
    Validator validator = new Validator();
    final int number = 1;
    assertTrue(validator.isValidNumber(number));
  }

  @Test
  public void testCorrectIsValidNumber2() {
    Validator validator = new Validator();
    final int number = 9;
    assertTrue(validator.isValidNumber(number));
  }

  @Test
  public void testCorrectIsValidNumber3() {
    Validator validator = new Validator();
    final int number = 0;
    assertFalse(validator.isValidNumber(number));
  }


  @Test
  public void testIncorrectIsValidNumber1() {
    Validator validator = new Validator();
    final int number = 0;
    assertFalse(validator.isValidNumber(number));
  }

  @Test
  public void testIncorrectIsValidNumber2() {
    Validator validator = new Validator();
    final int number = 10;
    assertFalse(validator.isValidNumber(number));
  }

  @Test
  public void testIncorrectIsValidNumber3() {
    Validator validator = new Validator();
    final int number = -1;
    assertFalse(validator.isValidNumber(number));
  }

  @Test
  public void testIncorrectIsValidNumber4() {
    Validator validator = new Validator();
    final int number = -100;
    assertFalse(validator.isValidNumber(number));
  }

  @Test
  public void testCorrectIsValidNumber5() {
    Validator validator = new Validator();
    final int number = 100;
    assertFalse(validator.isValidNumber(number));
  }

  @Test
  public void testDifferentConstructor1() {
    Validator validator = new Validator(1, 19);
    final int number = 19;
    assertTrue(validator.isValidNumber(number));
  }

  @Test
  public void testDifferentConstructor2() {
    Validator validator = new Validator(1, 19);
    final int number = 20;
    assertFalse(validator.isValidNumber(number));
  }

  @Test
  public void testDifferentConstructor3() {
    Validator validator = new Validator(1, 100);
    final int number = 100;
    assertTrue(validator.isValidNumber(number));
  }

  @Test
  public void testDifferentConstructor4() {
    Validator validator = new Validator(1, 100);
    final int number = 101;
    assertFalse(validator.isValidNumber(number));
  }

  @Test
  public void testDifferentConstructor5() {
    Validator validator = new Validator(0, 19);
    final int number = 0;
    assertTrue(validator.isValidNumber(number));
  }

  @Test
  public void testDifferentConstructor6() {
    Validator validator = new Validator(0, 19);
    final int number = -1;
    assertFalse(validator.isValidNumber(number));
  }

}
