package io.deeplay.intership.exception;

public class ServerException extends Exception {
    public final ServerErrorCode serverErrorCode;
    public final String message;

    public ServerException(ServerErrorCode serverErrorCode) {
        this.serverErrorCode = serverErrorCode;
        this.message = serverErrorCode.message;
    }
}
