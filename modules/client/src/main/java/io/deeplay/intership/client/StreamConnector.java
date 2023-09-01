package io.deeplay.intership.client;

import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.json.converter.JSONConverter;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StreamConnector {
    private final Logger logger = Logger.getLogger(StreamConnector.class);
    private final JSONConverter jsonConverter = new JSONConverter();
    private final DataOutputStream writer;
    private final DataInputStream reader;

    public StreamConnector(DataOutputStream writer, DataInputStream reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public void sendRequest(final String request) throws ClientException {
        try {
            writer.writeUTF(request);
            writer.flush();
        } catch (IOException e) {
            logger.error("Unknown IOException");
            throw new ClientException(ClientErrorCode.UNKNOWN_IO_EXCEPTION);
        }
    }

    public  <T extends BaseDtoResponse> T getResponse() throws ClientException {
        try {
            final String fromServer = reader.readUTF();
            return (T) jsonConverter.getObjectFromJson(fromServer, BaseDtoResponse.class);
        } catch (IOException ex) {
            logger.error(ex);
            throw new ClientException(ClientErrorCode.UNKNOWN_IO_EXCEPTION);
        }
    }

}
