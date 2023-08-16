package io.deeplay.intership.service;

import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.LogoutDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserServiceTest {
    private final UserService userService = new UserService();

    @Test
    public void testConstructor() {
        assertDoesNotThrow(UserService::new);
    }

    @Test
    public void testIsAuthorized() {
        String token = "";

        assertDoesNotThrow(() -> userService.isAuthorized(token));
        assertFalse(userService.isAuthorized(token));
    }

    @Test
    public void testAuthorization() {
        final String login = "admin";
        final String passwordHash = "1234567890";
        final LoginDtoRequest dto = new LoginDtoRequest(RequestType.LOGIN, login, passwordHash);

        assertDoesNotThrow(() -> userService.authorization(dto));
    }

    @Test
    public void testRegister() {
        final String login = "admin";
        final String passwordHash = "1234567890";
        final RegistrationDtoRequest dto = new RegistrationDtoRequest(
                RequestType.REGISTRATION,
                login,
                passwordHash
        );

        assertDoesNotThrow(() -> userService.register(dto));
    }

    @Test
    public void testLogout() {
        final String token = UUID.randomUUID().toString();
        final LogoutDtoRequest dto = new LogoutDtoRequest(
                RequestType.REGISTRATION,
                token);

        assertDoesNotThrow(() -> userService.logout(dto));
    }

}
