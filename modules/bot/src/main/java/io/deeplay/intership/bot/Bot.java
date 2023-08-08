package io.deeplay.intership.bot;

public class Bot {
    public String randomMove(){
        return String.format("%d %d", (int)(Math.random() * 9 + 1),(int)(Math.random() * 9 + 1));
    }
}
