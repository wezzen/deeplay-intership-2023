package io.deeplay.intership.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void mainThrowTest() {
        assertDoesNotThrow(() -> Client.main(new String[0]));
    }

}