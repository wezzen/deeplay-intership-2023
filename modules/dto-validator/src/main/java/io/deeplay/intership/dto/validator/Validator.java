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
        if (!isExistColor(dtoRequest.color())) {
            throw new ServerException(ErrorCode.COLOR_DOES_NOT_EXIST);
        }

        if (!isCorrectSize(dtoRequest.size())) {
            throw new ServerException(ErrorCode.SERVER_EXCEPTION);
        }

        if (isNotCorrectToken(dtoRequest.token())) {
            throw new ServerException(ErrorCode.NOT_AUTHORIZED);
        }
    }

    public void validationFinishGameDto(FinishGameDtoRequest dtoRequest) throws ServerException {
        if (isNotCorrectToken(dtoRequest.gameId())) {
            throw new ServerException(ErrorCode.NOT_AUTHORIZED);
        }
    }

    public void validationJoinGameDto(JoinGameDtoRequest dtoRequest) throws ServerException {
        if (isNotCorrectToken(dtoRequest.gameId())) {
            throw new ServerException(ErrorCode.INVALID_GAME_ID);
        }

        if (isNotCorrectToken(dtoRequest.token())) {
            throw new ServerException(ErrorCode.NOT_AUTHORIZED);
        }
    }

    public void validationLoginDto(LoginDtoRequest dtoRequest) throws ServerException {
        if (!isNotCorrectLogin(dtoRequest.login())) {
            throw new ServerException(ErrorCode.INCORRECT_LOGIN);
        }

    }

    public void validationLogoutDto(LogoutDtoRequest dtoRequest) throws ServerException {
        if (isNotCorrectToken(dtoRequest.token())) {
            throw new ServerException(ErrorCode.SERVER_EXCEPTION);
        }
    }

    public void validationPassDto(PassDtoRequest dtoRequest) throws ServerException {
        if (isNotCorrectToken(dtoRequest.token())) {
            throw new ServerException(ErrorCode.NOT_AUTHORIZED);
        }
    }

    public void validationRegistrationDto(RegistrationDtoRequest dtoRequest) throws ServerException {
        if (isNotCorrectLogin(dtoRequest.login())) {
            throw new ServerException(ErrorCode.INCORRECT_LOGIN);
        }

        if (isNotCorrectPassword(dtoRequest.passwordHash())) {
            throw new ServerException(ErrorCode.PASSWORD_CANNOT_BE_EMPTY);
        }
    }

    private boolean isExistColor(String color) {
        return color != null &&
                (color.equals(Color.BLACK.name()) ||
                        color.equals(Color.WHITE.name()) ||
                        color.equals(Color.EMPTY.name()));
    }

    private boolean isNotCorrectLogin(String login) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]*$");
        Matcher matcher = pattern.matcher(login);
        return login == null ||
                login.contains(" ") ||
                login.isBlank() ||
                matcher.matches();
    }

    private boolean isNotCorrectToken(String token) {
        if (token == null) {
            return true;
        }
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9-]*$");
        Matcher matcher = pattern.matcher(token);
        return token.contains(" ") ||
                token.isBlank() ||
                matcher.matches();
    }

    private boolean isCorrectSize(int size) {
        for (var item : FIELD_SIZES) {
            if (item == size) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotCorrectPassword(final String password) {
        return password == null ||
                password.isBlank();
    }
}
