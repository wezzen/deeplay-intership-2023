package io.deeplay.intership.dto.response;

public enum ResponseStatus {
    SUCCESS("success"),
    FAILURE("failure");

    public final String text;

    ResponseStatus(String text) {
        this.text = text;
    }
}
