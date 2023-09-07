package io.deeplay.intership.server;

import io.deeplay.intership.dto.request.LoginDtoRequest;
import io.deeplay.intership.dto.request.LogoutDtoRequest;
import io.deeplay.intership.dto.request.RegistrationDtoRequest;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.service.UserService;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private final DataCollectionsAggregator collectionsAggregator = mock(DataCollectionsAggregator.class);
    private final UserService userService = mock(UserService.class);
    private final Validator validator = mock(Validator.class);
    private final int id = 1;
    private final UserController userController = new UserController(userService, validator, id);

    @Test
    public void testConstructor() {
        assertAll(
                () -> assertDoesNotThrow(() -> new UserController(collectionsAggregator, id)),
                () -> assertDoesNotThrow(() -> new UserController(userService, validator, id))
        );
    }

    @Test
    public void testRegistrationSuccess() {
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                login,
                password);


        assertDoesNotThrow(() -> userController.registerUser(dtoRequest));
    }

    @Test
    public void testRegistrationFailure() throws ServerException {
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                login,
                password);

        when(userService.register(dtoRequest)).thenThrow(new ServerException(ErrorCode.LOGIN_IS_EXIST));

        assertDoesNotThrow(() -> userController.registerUser(dtoRequest));
    }

    @Test
    public void testLoginSuccess() {
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                password);


        assertDoesNotThrow(() -> userController.login(dtoRequest));
    }

    @Test
    public void testLoginFailure() throws ServerException {
        final String login = UUID.randomUUID().toString();
        final String password = UUID.randomUUID().toString();
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                password);
        final String response = dtoRequest.toString();

        when(userService.login(dtoRequest)).thenThrow(new ServerException(ErrorCode.NOT_FOUND_LOGIN));

        assertDoesNotThrow(() -> userController.login(dtoRequest));
    }

    @Test
    public void testLogoutSuccess() {
        final String token = UUID.randomUUID().toString();
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(token);


        assertDoesNotThrow(() -> userController.logout(dtoRequest));
    }

    @Test
    public void testLogoutFailure() throws ServerException {
        final String token = UUID.randomUUID().toString();
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(token);

        when(userService.logout(dtoRequest)).thenThrow(new ServerException(ErrorCode.NOT_AUTHORIZED));

        assertDoesNotThrow(() -> userController.logout(dtoRequest));
    }
}
