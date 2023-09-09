package io.deeplay.intership.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientExceptionTest {
    @Test
    public void testExceptionConstructor() {
        ClientException exceptionWrongInput = new ClientException(ClientErrorCode.WRONG_INPUT);
        ClientException exceptionNoSuchOption = new ClientException(ClientErrorCode.NO_SUCH_OPTIONS);

        assertAll(
                () -> assertEquals(ClientErrorCode.WRONG_INPUT, exceptionWrongInput.errorCode),
                () -> assertEquals(ClientErrorCode.WRONG_INPUT.message, exceptionWrongInput.errorMessage),
                () -> assertEquals(ClientErrorCode.NO_SUCH_OPTIONS, exceptionNoSuchOption.errorCode),
                () -> assertEquals(ClientErrorCode.NO_SUCH_OPTIONS.message, exceptionNoSuchOption.errorMessage)
        );
        assertThrows(ClientException.class, () -> {
            throw new ClientException(ClientErrorCode.WRONG_INPUT);
        });

    }
}
