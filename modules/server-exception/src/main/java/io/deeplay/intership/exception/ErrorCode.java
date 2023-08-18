package io.deeplay.intership.exception;

public enum ErrorCode {
    INVALID_REQUEST_TYPE("Invalid request type"),

    INCORRECT_LOGIN("Incorrect login"),
    LOGIN_IS_EXIST("This login is already exists"),
    NOT_FOUND_LOGIN("Cannot find this login"),
    PASSWORD_CANNOT_BE_EMPTY("Password can't be empty"),

    INVALID_AUTHORIZATION("Invalid login or password"),
    NOT_AUTHORIZED("You're not authorized"),
    BAD_HAPPENED("Something bad happened"),

    SERVER_EXCEPTION("Server is temporarily unavailable!"),

    HAS_BEEN_REACHED_MAX_PLAYER_LIMIT("There are two players already"),
    INVALID_MOVE("Invalid move"),

    CANNOT_FINISH_GAME("End of game haven't reached yet"),
    GAME_NOT_FOUND("Can't found game by this id"),

    COLOR_DOES_NOT_EXIST("This color is not exist"),
    INVALID_GAME_ID("Invalid game id"),
    INVALID_COLOR("Invalid color"),
    REPEATED_PLAYER("You're already connected to this game"),
    ;
    public final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
