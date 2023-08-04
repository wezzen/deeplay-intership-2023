package io.deeplay.intership.ui.terminal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testIsValidAction_WithCorrectData() {
        Validator validator = new Validator();
        final int input1 = 1;
        final int input2 = 2;
        final int input3 = 3;
        final int input4 = 4;
        final int input5 = 5;
        final int input6 = 6;
        final int input7 = 7;
        final int input8 = 8;

        assertAll(
                () -> assertTrue(validator.isValidAction(input1)),
                () -> assertTrue(validator.isValidAction(input2)),
                () -> assertTrue(validator.isValidAction(input3)),
                () -> assertTrue(validator.isValidAction(input4)),
                () -> assertTrue(validator.isValidAction(input5)),
                () -> assertTrue(validator.isValidAction(input6)),
                () -> assertTrue(validator.isValidAction(input7)),
                () -> assertTrue(validator.isValidAction(input8))
        );
    }

    @Test
    public void testIsValidAction_WithIncorrectData() {
        Validator validator = new Validator();
        final int input1 = Integer.MIN_VALUE;
        final int input2 = -1;
        final int input3 = 20;
        final int input4 = 100;

        assertAll(
                () -> assertFalse(validator.isValidAction(input1)),
                () -> assertFalse(validator.isValidAction(input2)),
                () -> assertFalse(validator.isValidAction(input3)),
                () -> assertFalse(validator.isValidAction(input4))
        );
    }

    @Test
    public void testIsColorAction_WithCorrectData() {
        Validator validator = new Validator();
        UserAction action1 = UserAction.CHOOSE_EMPTY_COLOR;
        UserAction action2 = UserAction.CHOOSE_EMPTY_COLOR;
        UserAction action3 = UserAction.CHOOSE_EMPTY_COLOR;

        assertAll(
                () -> assertTrue(validator.isColorAction(action1)),
                () -> assertTrue(validator.isColorAction(action2)),
                () -> assertTrue(validator.isColorAction(action3))
        );
    }

    @Test
    public void testIsColorAction_WithIncorrectData() {
        Validator validator = new Validator();
        UserAction action1 = UserAction.START_GAME;
        UserAction action2 = UserAction.SKIP;
        UserAction action3 = UserAction.MOVE;
        UserAction action4 = UserAction.CHOOSE_COLOR;
        UserAction action5 = UserAction.END_GAME;

        assertAll(
                () -> assertFalse(validator.isColorAction(action1)),
                () -> assertFalse(validator.isColorAction(action2)),
                () -> assertFalse(validator.isColorAction(action3)),
                () -> assertFalse(validator.isColorAction(action4)),
                () -> assertFalse(validator.isColorAction(action5)),
                () -> assertFalse(validator.isColorAction(null))
        );
    }

}
