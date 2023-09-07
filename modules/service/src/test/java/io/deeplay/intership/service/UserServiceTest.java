package io.deeplay.intership.service;

import io.deeplay.intership.dao.UserDao;
import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.LogoutDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.User;
import io.deeplay.intership.util.aggregator.AggregatorUtil;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private final DataCollectionsAggregator collectionsAggregator = new DataCollectionsAggregator();
    private final AggregatorUtil aggregatorUtil = mock(AggregatorUtil.class);
    private final UserDao userDao = mock(UserDao.class);
    private final UserService userService = new UserService(collectionsAggregator, userDao);

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new UserService(collectionsAggregator));
    }

    @Test
    public void testSuccessAuthorization() throws ServerException {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "1234567890";
        final LoginDtoRequest loginRequest = new LoginDtoRequest(login, passwordHash);
        final User user = new User(login, passwordHash);

        when(userDao.getUserByLogin(login)).thenReturn(Optional.of(user));

        final var loginResponse = userService.login(loginRequest);

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
        final LoginDtoRequest loginRequest = new LoginDtoRequest(login, passwordHash);
        final User user = new User(login, passwordHash);

        when(userDao.getUserByLogin(login)).thenReturn(Optional.of(user));

        final var firstResponse = userService.login(loginRequest);
        final var secondResponse = userService.login(loginRequest);

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
        assertThrows(ServerException.class, () -> userService.login(dtoWithInvalidPasswd));
    }

    @Test
    public void testSuccessRegister() throws ServerException {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "1234567890";
        final RegistrationDtoRequest registerRequest = new RegistrationDtoRequest(
                login,
                passwordHash
        );

        var info = userService.register(registerRequest);
        assertAll(
                () -> assertEquals(ResponseStatus.SUCCESS, info.status),
                () -> assertEquals(ResponseInfoMessage.SUCCESS_REGISTRATION.message, info.message)
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
                () -> assertDoesNotThrow(() -> userService.register(registerRequest))
        );
    }

    @Test
    public void testSuccessLogout() throws ServerException {
        final String token = UUID.randomUUID().toString();
        final LogoutDtoRequest logoutDto = new LogoutDtoRequest(token);

        final var result = userService.logout(logoutDto);
        final ResponseStatus expectedStatus = ResponseStatus.SUCCESS;
        final String expectedMessage = ResponseInfoMessage.SUCCESS_LOGOUT.message;
        assertAll(
                () -> assertEquals(expectedStatus, result.status),
                () -> assertEquals(expectedMessage, result.message)
        );
    }

}
