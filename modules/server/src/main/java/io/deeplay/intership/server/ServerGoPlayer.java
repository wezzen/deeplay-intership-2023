package io.deeplay.intership.server;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.dto.request.gameplay.AnswerDtoRequest;
import io.deeplay.intership.dto.request.gameplay.AnswerDtoType;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.model.Answer;
import io.deeplay.intership.model.AnswerType;
import io.deeplay.intership.model.Color;
import io.deeplay.intership.model.GoPlayer;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

public class ServerGoPlayer implements GoPlayer {
    private final StreamConnector streamConnector;
    private final Lock lock;
    private final String name;
    private final Color color;

    public ServerGoPlayer(final StreamConnector streamConnector, final Lock lock, final String name, final Color color) {
        this.streamConnector = streamConnector;
        this.lock = lock;
        this.name = name;
        this.color = color;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Answer getGameAction() {
        try {
            var dtoRequest = streamConnector.getRequest();
            if (dtoRequest instanceof final AnswerDtoRequest request) {
                return new Answer(
                        request.answerType == AnswerDtoType.TURN ? AnswerType.TURN : AnswerType.PASS,
                        request.row,
                        request.column);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        throw new IllegalStateException();
    }

    public void sendResponse(final BaseDtoResponse dtoResponse) throws IOException {
        streamConnector.sendResponse(dtoResponse);
    }

    @Override
    public void startGame() {
        lock.lock();
    }

    @Override
    public void endGame() {
        lock.unlock();
    }
}
