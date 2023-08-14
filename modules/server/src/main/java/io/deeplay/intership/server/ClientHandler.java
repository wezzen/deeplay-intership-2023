package io.deeplay.intership.server;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;


public class ClientHandler implements Runnable {
    private static final AtomicInteger clientIdCounter = new AtomicInteger(1);
    private final Logger logger = Logger.getLogger(ClientHandler.class);
    private final Socket clientSocket;
    private final int clientId;
    private final Converter converter;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.clientId = clientIdCounter.getAndAdd(1);
        this.converter = new Converter();
    }

    @Override
    public void run() {

        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {
            while (true) {
                //TODO: do something...
            }
        } catch (IOException e) {
            logger.debug(e.getMessage());
        } finally {
            try {
                clientSocket.close();
                logger.info("Client connection was closed");
            } catch (IOException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
