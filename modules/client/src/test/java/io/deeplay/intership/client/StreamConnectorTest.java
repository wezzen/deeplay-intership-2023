package io.deeplay.intership.client;

import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.json.converter.JSONConverter;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOError;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class StreamConnectorTest {
    private final DataInputStream dataInputStream = mock(DataInputStream.class);
    private final DataOutputStream dataOutputStream = mock(DataOutputStream.class);
    private final StreamConnector streamConnector = new StreamConnector(dataOutputStream, dataInputStream);
    private final JSONConverter jsonConverter = new JSONConverter();

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new StreamConnector(dataOutputStream, dataInputStream));
    }

    @Test
    public void testSendRequest() throws ClientException {
        final String string = "";

        assertDoesNotThrow(() -> streamConnector.sendRequest(string));
    }

    @Test
    public void testSendRequestFailure() throws ClientException, IOException {
        doThrow(new IOException()).when(dataOutputStream).writeUTF(anyString());

        assertThrows(ClientException.class, () -> streamConnector.sendRequest(""));
    }

    @Test
    public void testGetResponse() throws IOException {
        final InfoDtoResponse infoDtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS.text,
                ResponseInfoMessage.SUCCESS_LOGOUT.message);
        final String string = jsonConverter.getJsonFromObject(infoDtoResponse);

        when(dataInputStream.readUTF()).thenReturn(string);

        assertDoesNotThrow(() -> streamConnector.sendRequest(string));
    }


    @Test
    public void testGetResponseFailure() throws IOException {
        when(dataInputStream.readUTF()).thenThrow(IOException.class);

        assertThrows(ClientException.class, streamConnector::getResponse);
    }
}
