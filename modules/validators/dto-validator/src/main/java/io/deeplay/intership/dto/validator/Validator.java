package io.deeplay.intership.dto.validator;

import io.deeplay.intership.dto.request.authorization.LoginDtoRequest;
import io.deeplay.intership.dto.request.authorization.LogoutDtoRequest;
import io.deeplay.intership.dto.request.authorization.RegistrationDtoRequest;
import io.deeplay.intership.dto.request.game.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.game.JoinGameDtoRequest;
import io.deeplay.intership.dto.request.gameplay.AnswerDtoRequest;
import io.deeplay.intership.dto.request.gameplay.FinishGameDtoRequest;
import io.deeplay.intership.exception.ServerErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс {@link Validator} предоставляет методы для проверки данных, передаваемых в различных
 * запросах, и генерации исключений типа {@link ServerException} в случае неверных данных.
 */
public class Validator {
    // Массив допустимых размеров поля
    private final int[] FIELD_SIZES = {9, 11, 13, 15, 17, 19};

    /**
     * Проверяет данные из объекта {@link CreateGameDtoRequest}.
     *
     * @param dtoRequest Объект {@link CreateGameDtoRequest} для проверки.
     * @throws ServerException Если данные недействительны, генерируется исключение {@link ServerException}.
     */
    public void validationCreateGameDto(final CreateGameDtoRequest dtoRequest) throws ServerException {
        isExistColor(dtoRequest.color);
        isValidToken(dtoRequest.token);
        isValidSize(dtoRequest.size);
    }

    /**
     * Проверяет данные из объекта {@link FinishGameDtoRequest}.
     *
     * @param dtoRequest Объект {@link FinishGameDtoRequest} для проверки.
     * @throws ServerException Если данные недействительны, генерируется исключение {@link ServerException}.
     */
    public void validationFinishGameDto(final FinishGameDtoRequest dtoRequest) throws ServerException {
        isValidToken(dtoRequest.gameId);
    }

    /**
     * Проверяет данные из объекта {@link JoinGameDtoRequest}.
     *
     * @param dtoRequest Объект {@link JoinGameDtoRequest} для проверки.
     * @throws ServerException Если данные недействительны, генерируется исключение {@link ServerException}.
     */
    public void validationJoinGameDto(final JoinGameDtoRequest dtoRequest) throws ServerException {
        isValidToken(dtoRequest.gameId);
        isValidToken(dtoRequest.token);
        isExistColor(dtoRequest.color);
    }

    /**
     * Проверяет данные из объекта {@link LoginDtoRequest}.
     *
     * @param dtoRequest Объект {@link LoginDtoRequest} для проверки.
     * @throws ServerException Если данные недействительны, генерируется исключение {@link ServerException}.
     */
    public void validationLoginDto(final LoginDtoRequest dtoRequest) throws ServerException {
        isValidLogin(dtoRequest.login);
        isValidPassword(dtoRequest.passwordHash);
    }

    /**
     * Проверяет данные из объекта {@link LogoutDtoRequest}.
     *
     * @param dtoRequest Объект {@link LogoutDtoRequest} для проверки.
     * @throws ServerException Если данные недействительны, генерируется исключение {@link ServerException}.
     */
    public void validationLogoutDto(final LogoutDtoRequest dtoRequest) throws ServerException {
        isValidToken(dtoRequest.token);
    }

    /**
     * Проверяет данные из объекта {@link AnswerDtoRequest}.
     *
     * @param dtoRequest Объект {@link AnswerDtoRequest} для проверки.
     * @throws ServerException Если данные недействительны, генерируется исключение {@link ServerException}.
     */
    public void validationTurnDto(final AnswerDtoRequest dtoRequest) throws ServerException {
        isValidCoordinates(dtoRequest.row, dtoRequest.column);
    }

    /**
     * Проверяет данные из объекта {@link RegistrationDtoRequest}.
     *
     * @param dtoRequest Объект {@link RegistrationDtoRequest} для проверки.
     * @throws ServerException Если данные недействительны, генерируется исключение {@link ServerException}.
     */
    public void validationRegistrationDto(final RegistrationDtoRequest dtoRequest) throws ServerException {
        isValidLogin(dtoRequest.login);
        isValidPassword(dtoRequest.passwordHash);
    }

    private void isExistColor(final String color) throws ServerException {
        if (color == null) {
            throw new ServerException(ServerErrorCode.COLOR_DOES_NOT_EXIST);
        }
        try {
            Color.valueOf(color);
        } catch (IllegalArgumentException ex) {
            throw new ServerException(ServerErrorCode.COLOR_DOES_NOT_EXIST);
        }
    }

    private void isValidLogin(final String login) throws ServerException {
        if (login == null ||
                login.isBlank() ||
                login.contains(" ")) {
            throw new ServerException(ServerErrorCode.INCORRECT_LOGIN);
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(login);
        if (matcher.matches()) {
            throw new ServerException(ServerErrorCode.INCORRECT_LOGIN);
        }
    }

    private void isValidToken(final String token) throws ServerException {
        if (token == null ||
                token.isBlank() ||
                token.contains(" ")) {
            throw new ServerException(ServerErrorCode.NOT_AUTHORIZED);
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9-]");
        Matcher matcher = pattern.matcher(token);
        if (matcher.matches()) {
            throw new ServerException(ServerErrorCode.NOT_AUTHORIZED);
        }
    }

    private void isValidSize(final int size) throws ServerException {
        for (var item : FIELD_SIZES) {
            if (item == size) {
                return;
            }
        }
        throw new ServerException(ServerErrorCode.INVALID_BOARD_SIZE);
    }

    private void isValidPassword(final String password) throws ServerException {
        if (password == null ||
                password.isBlank()) {
            throw new ServerException(ServerErrorCode.PASSWORD_CANNOT_BE_EMPTY);
        }
    }

    private void isValidCoordinates(final int x, final int y) throws ServerException {
        if (x > FIELD_SIZES[FIELD_SIZES.length - 1] ||
                y > FIELD_SIZES[FIELD_SIZES.length - 1] ||
                x < 0 ||
                y < 0) {
            throw new ServerException(ServerErrorCode.TURN_HAS_INVALID_COORDINATES);
        }
    }
}
