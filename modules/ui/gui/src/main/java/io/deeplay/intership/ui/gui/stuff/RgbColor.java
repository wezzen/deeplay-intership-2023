package io.deeplay.intership.ui.gui.stuff;

public record RgbColor(int red, int green, int blue) {
    public int[] getRgbColor(){
        return new int[]{red, green, blue};
    }
}
