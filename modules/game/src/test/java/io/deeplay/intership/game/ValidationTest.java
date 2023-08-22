package io.deeplay.intership.game;

import io.deeplay.intership.model.Board;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.Group;
import io.deeplay.intership.validation.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {
    @Test
    public void testIsCorrectGroup() {
        final Board board = new Board();
        final Validation validation = new Validation(board);

        final int minX = 0;
        final int minY = 0;
        final int maxX = board.getField().length - 1;
        final int maxY = board.getField().length - 1;
        final Color blackColor = Color.BLACK;
        final Color whiteColor = Color.WHITE;

        board.getField()[minX][minY].setColor(blackColor);
        board.getField()[maxX][maxY].setColor(whiteColor);

        assertAll(
                () -> Assertions.assertDoesNotThrow(() -> validation.isCorrectMove(blackColor, minX, minY)),
                () -> assertFalse(validation.isCorrectMove(blackColor, minX, minY)),
                () -> Assertions.assertDoesNotThrow(() -> validation.isCorrectMove(whiteColor, maxX, maxY)),
                () -> assertFalse(validation.isCorrectMove(whiteColor, maxX, maxY))
        );
    }

    @Test
    public void testIsFortressForWhite() {
        final Board board = new Board();
        final Validation validation = new Validation(board);
        final Color whiteColor = Color.WHITE;
        GroupControl groupControl = new GroupControl(board.getField());

        board.getField()[0][0].setColor(whiteColor);
        groupControl.setGroup(board.getField()[0][0]);
        board.getField()[0][1].setColor(whiteColor);
        groupControl.setGroup(board.getField()[0][1]);
        board.getField()[0][2].setColor(whiteColor);
        groupControl.setGroup(board.getField()[0][2]);
        board.getField()[0][3].setColor(whiteColor);
        groupControl.setGroup(board.getField()[0][3]);
        board.getField()[0][4].setColor(whiteColor);
        groupControl.setGroup(board.getField()[0][4]);
        board.getField()[1][0].setColor(whiteColor);
        groupControl.setGroup(board.getField()[1][0]);
        board.getField()[1][2].setColor(whiteColor);
        groupControl.setGroup(board.getField()[1][2]);
        board.getField()[1][4].setColor(whiteColor);
        groupControl.setGroup(board.getField()[1][4]);
        board.getField()[2][0].setColor(whiteColor);
        groupControl.setGroup(board.getField()[2][0]);
        board.getField()[2][1].setColor(whiteColor);
        groupControl.setGroup(board.getField()[2][1]);
        board.getField()[2][2].setColor(whiteColor);
        groupControl.setGroup(board.getField()[2][2]);
        board.getField()[2][3].setColor(whiteColor);
        groupControl.setGroup(board.getField()[2][3]);
        board.getField()[2][4].setColor(whiteColor);
        groupControl.setGroup(board.getField()[2][4]);


        Group group = board.getField()[0][0].getGroup();

        assertAll(
                () -> Assertions.assertDoesNotThrow(() -> validation.isFortress(group)),
                () -> assertTrue(() -> validation.isFortress(group))
        );
    }

    @Test
    public void testIsFortressForBlack() {
        final Board board = new Board();
        final Validation validation = new Validation(board);
        final Color whiteColor = Color.BLACK;
        GroupControl groupControl = new GroupControl(board.getField());

        board.getField()[0][0].setColor(whiteColor);
        groupControl.setGroup(board.getField()[0][0]);
        board.getField()[0][1].setColor(whiteColor);
        groupControl.setGroup(board.getField()[0][1]);
        board.getField()[0][2].setColor(whiteColor);
        groupControl.setGroup(board.getField()[0][2]);
        board.getField()[0][3].setColor(whiteColor);
        groupControl.setGroup(board.getField()[0][3]);
        board.getField()[0][4].setColor(whiteColor);
        groupControl.setGroup(board.getField()[0][4]);
        board.getField()[1][0].setColor(whiteColor);
        groupControl.setGroup(board.getField()[1][0]);
        board.getField()[1][2].setColor(whiteColor);
        groupControl.setGroup(board.getField()[1][2]);
        board.getField()[1][4].setColor(whiteColor);
        groupControl.setGroup(board.getField()[1][4]);
        board.getField()[2][0].setColor(whiteColor);
        groupControl.setGroup(board.getField()[2][0]);
        board.getField()[2][1].setColor(whiteColor);
        groupControl.setGroup(board.getField()[2][1]);
        board.getField()[2][2].setColor(whiteColor);
        groupControl.setGroup(board.getField()[2][2]);
        board.getField()[2][3].setColor(whiteColor);
        groupControl.setGroup(board.getField()[2][3]);
        board.getField()[2][4].setColor(whiteColor);
        groupControl.setGroup(board.getField()[2][4]);


        Group group = board.getField()[0][0].getGroup();

        assertAll(
                () -> Assertions.assertDoesNotThrow(() -> validation.isFortress(group)),
                () -> assertTrue(() -> validation.isFortress(group))
        );
    }
}
