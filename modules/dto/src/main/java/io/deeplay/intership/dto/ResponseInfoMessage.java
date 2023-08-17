package io.deeplay.intership.dto;

public enum ResponseInfoMessage {
    SUCCESS_REGISTRATION("You was successfully registered!"),
    SUCCESS_AUTHORIZATION("You entered!"),
    SUCCESS_LOGOUT("You've log out!");
    public final String message;

    ResponseInfoMessage(String message) {
        this.message = message;
    }
}
