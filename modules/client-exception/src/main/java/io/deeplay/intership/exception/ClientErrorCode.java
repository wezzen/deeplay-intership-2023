package io.deeplay.intership.exception;

public enum ClientErrorCode {

    NO_SUCH_OPTIONS("There is not such options"),
    WRONG_INPUT("Wrong input"),
    ;
    public final String message;

    ClientErrorCode(String message) {
        this.message = message;
    }
}
