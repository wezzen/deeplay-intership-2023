package io.deeplay.intership.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ServerTest {

    @Test
    public void creatingInstanceTest() {
        assertDoesNotThrow(Server::new);
    }

}