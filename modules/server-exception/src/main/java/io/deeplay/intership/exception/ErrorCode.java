package io.deeplay.intership.exception;

public enum ErrorCode {
    INCORRECT_LOGIN("Incorrect login"),
    LOGIN_IS_EXIST("This login is already exists"),

    INVALID_AUTHORIZATION("Invalid login or password"),

    NOT_AUTHORIZED("You're not authorized"),
    BAD_HAPPENED("Something bad happened"),

    SERVER_EXCEPTION("Server is temporarily unavailable!"),

    HAS_BEEN_REACHED_MAX_PLAYER_LIMIT("There are two players already"),
    INVALID_MOVE("Invalid move"),

    CANNOT_FINISH_GAME("End of game haven't reached yet"),

    ;
    public final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
