package io.deeplay.intership.exception;

public enum ClientErrorCode {

    NO_SUCH_OPTIONS("There is not such options"),
    ;
    public final String message;

    ClientErrorCode(String message) {
        this.message = message;
    }
}
