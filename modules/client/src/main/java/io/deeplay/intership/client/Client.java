package io.deeplay.intership.client;

import io.deeplay.intership.UserInterface;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.terminal.DecisionMakerTerminal;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
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
    private static String host;
    private static int port;
    private static Socket socket;
    private static DataInputStream reader;
    private static DataOutputStream writer;
    private static UserInterface userInterface;
    private static DecisionMaker decisionMaker;
    private static StreamConnector streamConnector;
    private static GameController gameController;
    private static AuthorizationController authorizationController;
    private static String token;

    public Client(UserInterface ui, DecisionMaker maker, String host, int port) {
        init(ui, maker, host, port);
    }

    public Client() {
        init();
    }

    public static void main(String[] args) {
        token = authorizationController.authorizeClient();
        while (true) {
            try {
                gameController.joinToGame(token);
                gameController.processingGame();
            } catch (ClientException ex) {
                if (ex.errorCode == ClientErrorCode.NOT_AUTHORIZED_CLIENT) {
                    token = authorizationController.authorizeClient();
                }
            }
        }
    }

    public static void init(UserInterface ui, DecisionMaker maker, String host, int port) {
        userInterface = ui;
        decisionMaker = maker;

        try {
            socket = new Socket(InetAddress.getByName(host), port);
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        streamConnector = new StreamConnector(writer, reader);
        gameController = new GameController(streamConnector, userInterface, decisionMaker);
        authorizationController = new AuthorizationController(streamConnector, userInterface, decisionMaker);
    }

    public static void init() {
        UserInterfaceType userInterfaceType = UserInterfaceType.TERMINAL;
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH);) {
            Properties property = new Properties();
            property.load(fis);

            host = property.getProperty("client.host");
            port = Integer.parseInt(property.getProperty("client.port"));
            userInterfaceType = UserInterfaceType.valueOf(property.getProperty("client.GUI").toUpperCase());
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (userInterfaceType) {
            case SWING -> init();
            default -> init(new Display(), new DecisionMakerTerminal(new Scanner(System.in)), host, port);
        }
    }
}
