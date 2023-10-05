package io.deeplay.intership.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void testConstructor() {
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final User user = new User(login, password);
        assertAll(
                () -> assertNotNull(user.login()),
                () -> assertEquals(login, user.login()),
                () -> assertNotNull(user.passwordHash()),
                () -> assertEquals(password, user.passwordHash()),
                () -> assertNull(user.token())
        );
    }

    @Test
    public void testFullConstructor() {
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final User user = new User(login, password, token);
        assertAll(
                () -> assertNotNull(user.login()),
                () -> assertEquals(login, user.login()),
                () -> assertNotNull(user.passwordHash()),
                () -> assertEquals(password, user.passwordHash()),
                () -> assertNotNull(user.token()),
                () -> assertEquals(token, user.token())
        );
    }
}
