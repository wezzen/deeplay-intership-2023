package io.deeplay.intership.connection;

import io.deeplay.intership.dto.request.BaseDtoRequest;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.json.converter.JSONConverter;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Класс {@code StreamConnector} предоставляет функциональность для отправки запросов и получения
 * ответов с использованием сериализации JSON.
 */
public class StreamConnector {
    private final Logger logger = Logger.getLogger(StreamConnector.class);
    private final JSONConverter jsonConverter = new JSONConverter();
    private final DataOutputStream writer;
    private final DataInputStream reader;

    public StreamConnector(final DataOutputStream writer, final DataInputStream reader) {
        this.writer = writer;
        this.reader = reader;
    }

    /**
     * Отправляет запрос на сервер.
     *
     * @param dtoRequest Один из наследников класса {@link BaseDtoRequest} для отправки.
     * @throws IOException Если происходит ошибка ввода-вывода при отправке запроса, генерируется исключение {@code IOException}.
     */
    public void sendRequest(final BaseDtoRequest dtoRequest) throws IOException {
        try {
            writer.writeUTF(jsonConverter.getJsonFromObject(dtoRequest));
            writer.flush();
        } catch (IOException ex) {
            logger.error("Unknown IOException");
            throw ex;
        }
    }

    /**
     * Отправляет ответ c сервера.
     *
     * @param dtoResponse Один из наследников класса {@link BaseDtoRequest} для отправки.
     * @throws IOException Если происходит ошибка ввода-вывода при отправке ответа, генерируется исключение {@code IOException}.
     */
    public void sendResponse(final BaseDtoResponse dtoResponse) throws IOException {
        try {
            writer.writeUTF(jsonConverter.getJsonFromObject(dtoResponse));
            writer.flush();
        } catch (IOException ex) {
            logger.error("Unknown IOException");
            throw ex;
        }
    }

    /**
     * Получает ответ от сервера.
     *
     * @return Один из наследников класса {@link BaseDtoResponse}, полученный от сервера.
     * @throws IOException Если происходит ошибка ввода-вывода при получении ответа, генерируется исключение {@code IOException}.
     */

    public BaseDtoResponse getResponse() throws IOException {
        try {
            final String fromServer = reader.readUTF();
            return jsonConverter.getObjectFromJson(fromServer, BaseDtoResponse.class);
        } catch (IOException ex) {
            logger.error(ex);
            throw ex;
        }
    }

    /**
     * Получает запрос от клиента.
     *
     * @return Один из наследников класса {@link BaseDtoResponse}, полученный от сервера.
     * @throws IOException Если происходит ошибка ввода-вывода при получении запроса, генерируется исключение {@code IOException}.
     */
    public BaseDtoRequest getRequest() throws IOException {
        try {
            final String fromServer = reader.readUTF();
            return jsonConverter.getObjectFromJson(fromServer, BaseDtoRequest.class);
        } catch (IOException ex) {
            logger.error(ex);
            throw ex;
        }
    }
}
