package io.deeplay.intership.exception;

public class ServerException extends Exception {
    public final ErrorCode errorCode;

    public ServerException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
