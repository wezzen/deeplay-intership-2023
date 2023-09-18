package io.deeplay.intership.server;

import java.util.concurrent.locks.Lock;

public class PlayerThread extends Thread {
    private final ServerGame serverGame;
    private final Lock lock;

    public PlayerThread(ServerGame serverGame, Lock lock) {
        this.serverGame = serverGame;
        this.lock = lock;
        lock.lock();
    }

    @Override
    public void run() {
        while (!serverGame.isFinished()) {

        }
        lock.unlock();
    }
}
