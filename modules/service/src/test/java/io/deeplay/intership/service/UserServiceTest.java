package io.deeplay.intership.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    @Test
    public void testConstructor() {
        assertDoesNotThrow(UserService::new);
    }

    @Test
    public void testIsAuthorized() {
        UserService userService = new UserService();
        String token = "";

        assertDoesNotThrow(() -> userService.isAuthorized(token));
        assertFalse(userService.isAuthorized(token));
    }

    @Test
    public void testAuthorization() {

    }

    @Test
    public void testRegister() {

    }

    @Test
    public void testLogout() {

    }

}
