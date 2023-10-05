package io.deeplay.intership.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColorTest {
    @Test
    public void testInvertColor() {
        final Color blackColor = Color.BLACK;
        final Color whiteColor = Color.WHITE;
        final Color emptyColor = Color.EMPTY;

        final Color expectedWhite = Color.WHITE;
        final Color expectedBlack = Color.BLACK;

        assertAll(
                () -> assertDoesNotThrow(() -> Color.invertColor(blackColor)),
                () -> assertEquals(expectedWhite, Color.invertColor(blackColor)),

                () -> assertDoesNotThrow(() -> Color.invertColor(whiteColor)),
                () -> assertEquals(expectedBlack, Color.invertColor(whiteColor)),

                () -> assertThrows(IllegalStateException.class, () -> Color.invertColor(emptyColor)),
                () -> assertThrows(NullPointerException.class, () -> Color.invertColor(null))
        );
    }
}
