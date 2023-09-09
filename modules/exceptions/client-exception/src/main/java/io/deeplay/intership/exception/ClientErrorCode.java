package io.deeplay.intership.exception;

public enum ClientErrorCode {
    NOT_AUTHORIZED_CLIENT("Client is not authorized"),
    NO_SUCH_OPTIONS("There is no such options"),
    WRONG_INPUT("Wrong input"),
    UNKNOWN_IO_EXCEPTION("Unknown IOException"),
    ;
    public final String message;

    ClientErrorCode(String message) {
        this.message = message;
    }
}
