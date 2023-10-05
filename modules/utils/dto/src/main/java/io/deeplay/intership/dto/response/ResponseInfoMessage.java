package io.deeplay.intership.dto.response;

public enum ResponseInfoMessage {
    SUCCESS_REGISTRATION("You was successfully registered!"),
    SUCCESS_AUTHORIZATION("You entered!"),
    SUCCESS_LOGOUT("You've log out!"),
    SUCCESS_CREATE_GAME("Have a good game!"),
    SUCCESS_JOIN_GAME("Have a good game!"),
    SUCCESS_TURN("You turned"),
    CAN_TURN("You can move!"),
    SUCCESS_PASS("You can pass!"),
    SUCCESS_FINISH_GAME("Game was finished"),
    ;

    public final String message;

    ResponseInfoMessage(String message) {
        this.message = message;
    }
}
