package io.deeplay.intership.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void creatingInstanceTest() {
        assertDoesNotThrow(() -> new Client());
        assertDoesNotThrow(() -> new Client("172.0.0.1", 8000));
    }
    @Test
    void clientProcessTest() throws IOException {
            Client client = new Client();
            assertDoesNotThrow(() -> client.clientProcess());
    }


}