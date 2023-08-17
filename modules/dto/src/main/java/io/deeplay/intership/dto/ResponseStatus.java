package io.deeplay.intership.dto;

public enum ResponseStatus {
    SUCCESS("success"),
    FAILURE("failure");

    public final String text;

    ResponseStatus(String text) {
        this.text = text;
    }
}
