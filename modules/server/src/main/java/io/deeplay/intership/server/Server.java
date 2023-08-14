package io.deeplay.intership.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class);
    private static final int PORT = 5000;

    public static void main(final String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        logger.info("Server was started...");
        logger.info("Port number " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException ex) {
            logger.debug(ex.getMessage());
        }
        executorService.shutdown();
    }

}
