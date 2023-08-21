package io.deeplay.intership.dto.validator;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private final int[] FIELD_SIZES = {9, 11, 13, 15, 17, 19};

    public void validationCreateGameDto(CreateGameDtoRequest dtoRequest) throws ServerException {
        isExistColor(dtoRequest.color());
        isNotCorrectToken(dtoRequest.token());
        isCorrectSize(dtoRequest.size());
    }

    public void validationFinishGameDto(FinishGameDtoRequest dtoRequest) throws ServerException {
        isNotCorrectToken(dtoRequest.gameId());
    }

    public void validationJoinGameDto(JoinGameDtoRequest dtoRequest) throws ServerException {
        isNotCorrectToken(dtoRequest.gameId());
        isNotCorrectToken(dtoRequest.token());
        isExistColor(dtoRequest.color());
    }

    public void validationLoginDto(LoginDtoRequest dtoRequest) throws ServerException {
        isNotCorrectLogin(dtoRequest.login());
        isNotCorrectPassword(dtoRequest.passwordHash());
    }

    public void validationLogoutDto(LogoutDtoRequest dtoRequest) throws ServerException {
        isNotCorrectToken(dtoRequest.token());
    }

    public void validationTurnDto(TurnDtoRequest dtoRequest) throws ServerException {
        isNotCorrectToken(dtoRequest.token());
        isExistColor(dtoRequest.color());
        if (isInvalidCoordinates(dtoRequest.row(), dtoRequest.column())) {
            throw new ServerException(ErrorCode.TURN_HAS_INVALID_COORDINATES);
        }
    }

    public void validationPassDto(PassDtoRequest dtoRequest) throws ServerException {
        isNotCorrectToken(dtoRequest.token());
    }

    public void validationRegistrationDto(RegistrationDtoRequest dtoRequest) throws ServerException {
        isNotCorrectLogin(dtoRequest.login());
        isNotCorrectPassword(dtoRequest.passwordHash());
    }

    private void isExistColor(String color) throws ServerException {
        if (color == null) {
            throw new ServerException(ErrorCode.COLOR_DOES_NOT_EXIST);
        }
        try {
            Color.valueOf(color);
        } catch (IllegalArgumentException ex) {
            throw new ServerException(ErrorCode.COLOR_DOES_NOT_EXIST);
        }
    }

    private void isNotCorrectLogin(String login) throws ServerException {
        if (login == null ||
                login.isBlank() ||
                login.contains(" ")) {
            throw new ServerException(ErrorCode.INCORRECT_LOGIN);
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(login);
        if (matcher.matches()) {
            throw new ServerException(ErrorCode.INCORRECT_LOGIN);
        }
    }

    private void isNotCorrectToken(String token) throws ServerException {
        if (token == null ||
                token.isBlank() ||
                token.contains(" ")) {
            throw new ServerException(ErrorCode.NOT_AUTHORIZED);
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9-]");
        Matcher matcher = pattern.matcher(token);
        if (matcher.matches()) {
            throw new ServerException(ErrorCode.NOT_AUTHORIZED);
        }
    }

    private void isCorrectSize(int size) throws ServerException {
        for (var item : FIELD_SIZES) {
            if (item == size) {
                return;
            }
        }
        throw new ServerException(ErrorCode.INVALID_BOARD_SIZE);
    }

    private void isNotCorrectPassword(final String password) throws ServerException {
        if (password == null ||
                password.isBlank()) {
            throw new ServerException(ErrorCode.PASSWORD_CANNOT_BE_EMPTY);
        }
    }

    private boolean isInvalidCoordinates(int x, int y) {
        return x < 0 ||
                y < 0 ||
                y > FIELD_SIZES[FIELD_SIZES.length - 1] ||
                x > FIELD_SIZES[FIELD_SIZES.length - 1];
    }
}
