package io.deeplay.intership.server;

public class PlayerThread implements Runnable {
    private final ServerGame serverGame;

    public PlayerThread(ServerGame serverGame) {
        this.serverGame = serverGame;
    }

    @Override
    public void run() {
        while (!serverGame.isFinished()) {

        }
    }
}
