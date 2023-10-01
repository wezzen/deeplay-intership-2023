package io.deeplay.intership.server;

import java.util.concurrent.locks.Lock;

/**
 * Класс PlayerThread, представляющий поток, отвечающий за выполнение игры между игроками в рамках ServerGame.
 * Этот класс используется для управления ходами игры и обеспечивает синхронизацию между игроками.
 */
public class PlayerThread extends Thread {
    private final ServerGame serverGame;
    private final Lock lock;

    public PlayerThread(final ServerGame serverGame, final Lock lock) {
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
