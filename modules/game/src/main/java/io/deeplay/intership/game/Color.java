package io.deeplay.intership.game;

public enum Color {
    BLACK,
    WHITE,
    EMPTY;


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
