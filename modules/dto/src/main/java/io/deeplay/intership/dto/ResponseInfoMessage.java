package io.deeplay.intership.dto;

public enum ResponseInfoMessage {
    SUCCESS_REGISTRATION("You was successfully registered!"),
    SUCCESS_AUTHORIZATION("You entered!"),
    SUCCESS_LOGOUT("You've log out!"),
    SUCCESS_CREATE_GAME("Have a good game!"),
    SUCCESS_JOIN_GAME("Have a good game!"),
    SUCCESS_TURN("You can move!"),

    ;

    public final String message;

    ResponseInfoMessage(String message) {
        this.message = message;
    }
}
