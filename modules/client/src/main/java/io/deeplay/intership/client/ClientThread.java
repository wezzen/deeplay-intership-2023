package io.deeplay.intership.client;

import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
import io.deeplay.intership.model.Color;

import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Socket socket;
    private final AuthorizationController authorizationController;
    private final GameController gameController;
    private final GameplayController gameplayController;

    public ClientThread(
            final Socket socket,
            final AuthorizationController authorizationController,
            final GameController gameController,
            final GameplayController gameplayController) {
        this.socket = socket;
        this.authorizationController = authorizationController;
        this.gameController = gameController;
        this.gameplayController = gameplayController;
    }

    @Override
    public void run() {
        String token = authorizationController.authorizeClient();
        while (!socket.isClosed()) {
            try {
                Color color = gameController.joinToGame(token);
                gameplayController.processingGame(token, color);
            } catch (ClientException ex) {
                if (ex.errorCode == ClientErrorCode.NOT_AUTHORIZED_CLIENT) {
                    token = authorizationController.authorizeClient();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
