package io.deeplay.intership.exception;

public class ServerException extends Exception {
    public final ServerErrorCode errorCode;
    public final String message;

    public ServerException(ServerErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.message;
    }
}
