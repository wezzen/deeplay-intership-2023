package io.deeplay.intership.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void creatingInstanceTest() {
        assertDoesNotThrow(Server::new);
    }

}