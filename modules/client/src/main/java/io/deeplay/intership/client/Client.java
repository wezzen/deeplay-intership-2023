package io.deeplay.intership.client;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.gui.DecisionMakerGui;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.decision.maker.terminal.DecisionMakerTerminal;
import io.deeplay.intership.ui.UserInterface;
import io.deeplay.intership.ui.gui.DisplayGui;
import io.deeplay.intership.ui.terminal.Display;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class Client {
    private static final String CONFIG_PATH = "src/main/resources/config.properties";

    public static void main(String[] args) {
        try {
            final ClientProperties clientProperties = readProperties();
            final Socket socket = new Socket(InetAddress.getByName(clientProperties.host()), clientProperties.port());
            final UserInterfaceType userInterfaceType = UserInterfaceType.valueOf(clientProperties.interfaceType());
            final StreamConnector streamConnector = initConnector(socket);

            final UserInterface userInterface;
            final DecisionMaker decisionMaker;
            switch (userInterfaceType) {
                case SWING -> {
                    final ScannerGui scannerGui = new ScannerGui();
                    userInterface = new DisplayGui(scannerGui);
                    decisionMaker = new DecisionMakerGui(scannerGui);
                }
                case TERMINAL -> {
                    userInterface = new Display();
                    decisionMaker = new DecisionMakerTerminal(new Scanner(System.in));
                }
                default -> throw new IllegalStateException();
            }
            final GameController gameController = new GameController(streamConnector, userInterface, decisionMaker);
            final GameplayController gameplayController = new GameplayController(streamConnector, userInterface, decisionMaker);
            final AuthorizationController authorizationController = new AuthorizationController(streamConnector, userInterface, decisionMaker);

            final ClientThread clientThread = new ClientThread(
                    socket,
                    authorizationController,
                    gameController,
                    gameplayController);
            clientThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ClientProperties readProperties() throws IOException {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            final Properties property = new Properties();
            property.load(fis);

            final String host = property.getProperty("client.host");
            final int port = Integer.parseInt(property.getProperty("client.port"));
            final String userInterfaceType = property.getProperty("client.GUI").toUpperCase();
            return new ClientProperties(host, port, userInterfaceType);
        } catch (IOException ex) {
            throw ex;
        }
    }

    private static StreamConnector initConnector(final Socket socket) throws IOException {
        return new StreamConnector(
                new DataOutputStream(socket.getOutputStream()),
                new DataInputStream(socket.getInputStream())
        );
    }
}