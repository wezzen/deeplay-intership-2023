package io.deeplay.intership.exception;

public class ClientException extends Exception {
    public final ClientErrorCode errorCode;
    public final String errorMessage;

    public ClientException(ClientErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.message;
    }
}
