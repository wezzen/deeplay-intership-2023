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

/**
 * Реализация интерфейса {@link GoPlayer} для серверного игрока.
 * Класс предоставляет логику для взаимодействия серверного игрока с игрой через сетевое соединение.
 */
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

    /**
     * Получение имени игрока.
     *
     * @return Имя игрока.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Получение цвета игрока.
     *
     * @return Цвет игрока.
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Получение игрового действия от игрока.
     *
     * @return Объект типа {@link Answer}, представляющий игровое действие игрока.
     * @throws IllegalStateException Если не удалось получить корректный запрос от клиента.
     */
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

    /**
     * Отправка ответа клиенту.
     *
     * @param dtoResponse Ответ, который нужно отправить клиенту.
     * @throws IOException Если возникают ошибки при отправке ответа.
     */
    public void sendResponse(final BaseDtoResponse dtoResponse) throws IOException {
        streamConnector.sendResponse(dtoResponse);
    }

    /**
     * Начало игры для игрока.
     */
    @Override
    public void startGame() {
        lock.lock();
    }

    /**
     * Завершение игры для игрока.
     */
    @Override
    public void endGame() {
        lock.unlock();
    }
}
