package io.deeplay.intership.server;

import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.Player;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class);
    private static final int PORT = 5000;

    public static void main(final String[] args) {
        logger.info("Server was started...");
        logger.info("Port number " + PORT);
        final ExecutorService executorService = Executors.newCachedThreadPool();
        final ConcurrentMap<String, GameSession> idToGameSession = new ConcurrentHashMap<>();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ClientHandler(clientSocket, idToGameSession));
            }
        } catch (IOException ex) {
            logger.debug(ex.getMessage());
        }
        executorService.shutdown();
    }

}
