import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.dto.response.BaseDtoResponse;
import io.deeplay.intership.dto.response.InfoDtoResponse;
import io.deeplay.intership.dto.response.ResponseInfoMessage;
import io.deeplay.intership.dto.response.ResponseStatus;
import io.deeplay.intership.json.converter.JSONConverter;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ServerStreamConnectorTest {
    private final DataInputStream dataInputStream = mock(DataInputStream.class);
    private final DataOutputStream dataOutputStream = mock(DataOutputStream.class);
    private final StreamConnector streamConnector = new StreamConnector(dataOutputStream, dataInputStream);
    private final JSONConverter jsonConverter = new JSONConverter();

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new StreamConnector(dataOutputStream, dataInputStream));
    }

    @Test
    public void testSendRequest() throws IOException {
        final BaseDtoResponse dtoResponse = new BaseDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_AUTHORIZATION.message);

        assertDoesNotThrow(() -> streamConnector.sendResponse(dtoResponse));
    }

    @Test
    public void testSendRequestFailure() throws IOException {
        final BaseDtoResponse dtoResponse = new BaseDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_AUTHORIZATION.message);
        doThrow(new IOException()).when(dataOutputStream).writeUTF(anyString());

        assertThrows(IOException.class, () -> streamConnector.sendResponse(dtoResponse));
    }

    @Test
    public void testGetResponse() throws IOException {
        final BaseDtoResponse dtoResponse = new BaseDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_AUTHORIZATION.message);
        final InfoDtoResponse infoDtoResponse = new InfoDtoResponse(
                ResponseStatus.SUCCESS,
                ResponseInfoMessage.SUCCESS_LOGOUT.message);
        final String string = infoDtoResponse.toString();

        when(dataInputStream.readUTF()).thenReturn(string);

        assertDoesNotThrow(() -> streamConnector.sendResponse(dtoResponse));
    }


    @Test
    public void testGetResponseFailure() throws IOException {
        when(dataInputStream.readUTF()).thenThrow(IOException.class);

        assertThrows(IOException.class, streamConnector::getRequest);
    }
}
