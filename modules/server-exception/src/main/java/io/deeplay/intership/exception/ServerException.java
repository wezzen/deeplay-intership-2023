package io.deeplay.intership.exception;

public class ServerException extends Exception {
    public final ErrorCode errorCode;
    public final String message;

    public ServerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.message;
    }
}
