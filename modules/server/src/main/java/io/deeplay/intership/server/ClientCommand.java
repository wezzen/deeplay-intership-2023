package io.deeplay.intership.server;

public enum ClientCommand {
    REGISTRATION("REGISTRATION"),
    LOGIN("AUTHORIZATION"),
    LOGOUT("LOGIN"),

    CREATE_GAME("CREATE_GAME"),
    JOIN_GAME("JOIN"),
    SURRENDER_GAME("SURRENDER"),
    END_GAME("FINISH"),

    TURN("TURN"),
    PASS("PASS"),
    ;


    public final String command;

    ClientCommand(String command) {
        this.command = command;
    }
}
