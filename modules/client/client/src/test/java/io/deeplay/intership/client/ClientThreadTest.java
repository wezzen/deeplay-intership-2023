package io.deeplay.intership.client;

import io.deeplay.intership.client.controllers.AuthorizationController;
import io.deeplay.intership.client.controllers.GameController;
import io.deeplay.intership.client.controllers.GameplayController;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

public class ClientThreadTest {
    private final Socket socket = mock(Socket.class);
    private final AuthorizationController authorizationController = mock(AuthorizationController.class);
    private final GameController gameController = mock(GameController.class);
    private final GameplayController gameplayController = mock(GameplayController.class);

    @Test
    public void test() {
        assertDoesNotThrow(() -> new ClientThread(socket, authorizationController, gameController, gameplayController));
    }
}
