package io.deeplay.intership.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testConstructor() {
        final String login = "admin";
        final String color = Color.WHITE.name();
        final Player player = new Player(login, color);

        assertAll(
                () -> assertDoesNotThrow(player::login),
                () -> assertNotNull(player.login()),
                () -> assertEquals(login, player.login()),

                () -> assertDoesNotThrow(player::color),
                () -> assertNotNull(player.color()),
                () -> assertEquals(color, player.color())
        );
    }

    @Test
    public void testPlayerNull() {
        final Player player = new Player(null, null);

        assertAll(
                () -> assertDoesNotThrow(player::login),
                () -> assertDoesNotThrow(player::color),
                () -> assertNull(player.login()),
                () -> assertNull(player.color())
        );
    }
}
