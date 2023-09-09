package io.deeplay.intership.dto.validator;

import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.exception.ServerErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private final int[] FIELD_SIZES = {9, 11, 13, 15, 17, 19};

    public void validationCreateGameDto(CreateGameDtoRequest dtoRequest) throws ServerException {
        isExistColor(dtoRequest.color);
        isValidToken(dtoRequest.token);
        isValidSize(dtoRequest.size);
    }

    public void validationFinishGameDto(FinishGameDtoRequest dtoRequest) throws ServerException {
        isValidToken(dtoRequest.gameId);
    }

    public void validationJoinGameDto(JoinGameDtoRequest dtoRequest) throws ServerException {
        isValidToken(dtoRequest.gameId);
        isValidToken(dtoRequest.token);
        isExistColor(dtoRequest.color);
    }

    public void validationLoginDto(LoginDtoRequest dtoRequest) throws ServerException {
        isValidLogin(dtoRequest.login);
        isValidPassword(dtoRequest.passwordHash);
    }

    public void validationLogoutDto(LogoutDtoRequest dtoRequest) throws ServerException {
        isValidToken(dtoRequest.token);
    }

    public void validationTurnDto(TurnDtoRequest dtoRequest) throws ServerException {
        isValidToken(dtoRequest.token);
        isExistColor(dtoRequest.color);
        isValidCoordinates(dtoRequest.row, dtoRequest.column);
    }

    public void validationPassDto(PassDtoRequest dtoRequest) throws ServerException {
        isValidToken(dtoRequest.token);
    }

    public void validationRegistrationDto(RegistrationDtoRequest dtoRequest) throws ServerException {
        isValidLogin(dtoRequest.login);
        isValidPassword(dtoRequest.passwordHash);
    }

    private void isExistColor(String color) throws ServerException {
        if (color == null) {
            throw new ServerException(ServerErrorCode.COLOR_DOES_NOT_EXIST);
        }
        try {
            Color.valueOf(color);
        } catch (IllegalArgumentException ex) {
            throw new ServerException(ServerErrorCode.COLOR_DOES_NOT_EXIST);
        }
    }

    private void isValidLogin(String login) throws ServerException {
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

    private void isValidToken(String token) throws ServerException {
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

    private void isValidSize(int size) throws ServerException {
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

    private void isValidCoordinates(int x, int y) throws ServerException {
        if (x > FIELD_SIZES[FIELD_SIZES.length - 1] ||
                y > FIELD_SIZES[FIELD_SIZES.length - 1] ||
                x < 0 ||
                y < 0) {
            throw new ServerException(ServerErrorCode.TURN_HAS_INVALID_COORDINATES);
        }
    }
}
