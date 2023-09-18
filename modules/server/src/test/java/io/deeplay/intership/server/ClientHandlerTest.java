package io.deeplay.intership.server;

import io.deeplay.intership.server.controllers.GameController;
import io.deeplay.intership.server.controllers.UserController;
import io.deeplay.intership.util.aggregator.DataCollectionsAggregator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

public class ClientHandlerTest {
    private static Socket socket = mock(Socket.class);
    private final GameManager gameManager = mock(GameManager.class);
    private final UserController userController = mock(UserController.class);
    private final GameController gameController = mock(GameController.class);


    @Test
    public void testConstructors() {
        final DataCollectionsAggregator collectionsAggregator = new DataCollectionsAggregator();

        assertAll(
                () -> assertDoesNotThrow(() -> new ClientHandler(socket, collectionsAggregator, gameManager)),
                () -> assertDoesNotThrow(() -> new ClientHandler(socket, userController, gameController, gameManager))
        );
    }
}
