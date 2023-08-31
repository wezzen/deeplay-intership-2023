package io.deeplay.intership.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Color {
    BLACK("B"),
    WHITE("W"),
    EMPTY("E");
    public final String symbol;

    @JsonCreator
    Color(@JsonProperty("symbol") String symbol) {
        this.symbol = symbol;
    }

    /**
     * Получает цвет противника для данного цвета.
     *
     * @param color цвет, для которого требуется цвет оппонента
     * @return цвет соперника
     */
    public static Color invertColor(Color color) {
        return switch (color) {
            case BLACK -> WHITE;
            case WHITE -> BLACK;
            default -> throw new IllegalStateException();
        };
    }
}
