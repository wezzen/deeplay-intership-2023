package io.deeplay.intership.service;

import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.LogoutDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.exception.ServerException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private final UserService userService = new UserService();

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new UserService());
    }

    @Test
    public void testSuccessAuthorization() throws ServerException {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "1234567890";
        final RegistrationDtoRequest registrationRequest =
                new RegistrationDtoRequest(login, passwordHash);
        final LoginDtoRequest loginRequest = new LoginDtoRequest(login, passwordHash);

        userService.register(registrationRequest);
        final var loginResponse = userService.authorization(loginRequest);

        final ResponseStatus expectedStatus = ResponseStatus.SUCCESS;
        final String expectedMessage = ResponseInfoMessage.SUCCESS_AUTHORIZATION.message;
        assertAll(
                () -> assertEquals(expectedStatus, loginResponse.status),
                () -> assertEquals(expectedMessage, loginResponse.message),
                () -> assertNotNull(loginResponse.token)
        );
    }

    @Test
    public void testSuccessAuthorization_SecondAuth() throws ServerException {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "1234567890";
        final RegistrationDtoRequest registrationRequest =
                new RegistrationDtoRequest(login, passwordHash);
        final LoginDtoRequest loginRequest = new LoginDtoRequest(login, passwordHash);

        userService.register(registrationRequest);
        final var firstResponse = userService.authorization(loginRequest);
        final var secondResponse = userService.authorization(loginRequest);

        final ResponseStatus expectedStatus = ResponseStatus.SUCCESS;
        final String expectedMessage = ResponseInfoMessage.SUCCESS_AUTHORIZATION.message;
        assertAll(
                () -> assertEquals(expectedStatus, firstResponse.status),
                () -> assertEquals(expectedStatus, secondResponse.status),

                () -> assertEquals(expectedMessage, firstResponse.message),
                () -> assertEquals(expectedMessage, secondResponse.message),

                () -> assertNotEquals(firstResponse.token, secondResponse.token)
        );
    }

    @Test
    public void testFailureAuthorization() throws ServerException {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "1234567890";
        final RegistrationDtoRequest registrationRequest =
                new RegistrationDtoRequest(login, passwordHash);
        final LoginDtoRequest dtoWithInvalidPasswd = new LoginDtoRequest(login, "passwordHash");

        userService.register(registrationRequest);
        assertThrows(ServerException.class, () -> userService.authorization(dtoWithInvalidPasswd));
    }

    @Test
    public void testSuccessRegister() throws ServerException {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "1234567890";
        final RegistrationDtoRequest registerRequest = new RegistrationDtoRequest(
                login,
                passwordHash
        );

        LoginDtoRequest loginRequest = new LoginDtoRequest(
                login,
                passwordHash);

        var info = userService.register(registerRequest);
        var result = userService.authorization(loginRequest);

        assertAll(
                () -> assertEquals(ResponseStatus.SUCCESS, info.status),
                () -> assertEquals(ResponseInfoMessage.SUCCESS_REGISTRATION.message, info.message),
                () -> assertEquals(ResponseStatus.SUCCESS, result.status),
                () -> assertEquals(ResponseInfoMessage.SUCCESS_AUTHORIZATION.message, result.message)
        );
    }

    @Test
    public void testFailureRegister() throws ServerException {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "1234567890";
        final RegistrationDtoRequest registerRequest = new RegistrationDtoRequest(
                login,
                passwordHash
        );

        final var response = userService.register(registerRequest);
        final ResponseStatus expectedStatus = ResponseStatus.SUCCESS;
        final String expectedMessage = ResponseInfoMessage.SUCCESS_REGISTRATION.message;
        assertAll(
                () -> assertEquals(expectedStatus, response.status),
                () -> assertEquals(expectedMessage, response.message),
                () -> assertThrows(ServerException.class, () -> userService.register(registerRequest))
        );
    }

    @Test
    public void testSuccessLogout() throws ServerException {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "1234567890";
        final RegistrationDtoRequest registerRequest =
                new RegistrationDtoRequest(login, passwordHash);
        final LoginDtoRequest loginRequest = new LoginDtoRequest(login, passwordHash);

        userService.register(registerRequest);
        final var loginDtoResponse = userService.authorization(loginRequest);
        final LogoutDtoRequest logoutDto = new LogoutDtoRequest(loginDtoResponse.token);
        final var result = userService.logout(logoutDto);

        final ResponseStatus expectedStatus = ResponseStatus.SUCCESS;
        final String expectedMessage = ResponseInfoMessage.SUCCESS_LOGOUT.message;
        assertAll(
                () -> assertEquals(expectedStatus, result.status),
                () -> assertEquals(expectedMessage, result.message)
        );
    }

    @Test
    public void testFailureLogout() {
        final String token = UUID.randomUUID().toString();
        final LogoutDtoRequest dto = new LogoutDtoRequest(token);

        assertThrows(ServerException.class, () -> userService.logout(dto));
    }

}
