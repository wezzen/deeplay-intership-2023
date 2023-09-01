package io.deeplay.intership.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ClientTest {
    @Test
    void creatingInstanceTest() {
        assertDoesNotThrow(()->new Client());

    }
}