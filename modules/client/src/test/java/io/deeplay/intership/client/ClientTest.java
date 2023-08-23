package io.deeplay.intership.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void creatingInstanceTest() {
        assertDoesNotThrow(() -> Client.init());
    }
    @Test
    void clientProcessTest() throws IOException {
    }


}