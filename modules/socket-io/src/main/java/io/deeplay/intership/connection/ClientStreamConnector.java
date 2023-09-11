package io.deeplay.intership.connection;

import io.deeplay.intership.dto.request.BaseDtoRequest;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.json.converter.JSONConverter;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClientStreamConnector {
    private final Logger logger = Logger.getLogger(ClientStreamConnector.class);
    private final JSONConverter jsonConverter = new JSONConverter();
    private final DataOutputStream writer;
    private final DataInputStream reader;

    public ClientStreamConnector(DataOutputStream writer, DataInputStream reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public void sendRequest(final BaseDtoRequest dtoRequest) throws IOException {
        try {
            writer.writeUTF(jsonConverter.getJsonFromObject(dtoRequest));
            writer.flush();
        } catch (IOException ex) {
            logger.error("Unknown IOException");
            throw ex;
        }
    }

    public BaseDtoResponse getResponse() throws IOException {
        try {
            final String fromServer = reader.readUTF();
            return jsonConverter.getObjectFromJson(fromServer, BaseDtoResponse.class);
        } catch (IOException ex) {
            logger.error(ex);
            throw ex;
        }
    }
}
