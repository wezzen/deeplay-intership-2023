package io.deeplay.intership.client;

import io.deeplay.intership.connection.StreamConnector;
import io.deeplay.intership.ui.UserInterface;
import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.decision.maker.gui.DecisionMakerGui;
import io.deeplay.intership.decision.maker.gui.ScannerGui;
import io.deeplay.intership.decision.maker.terminal.DecisionMakerTerminal;
import io.deeplay.intership.exception.ClientErrorCode;
import io.deeplay.intership.exception.ClientException;
<<<<<<<HEAD
import io.deeplay.intership.ui.UserInterface;
import io.deeplay.intership.ui.gui.DisplayGui;
=======
import io.deeplay.intership.model.Color;
>>>>>>>fix:изменена клиентская часть для инверсии управления игрой
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
    private static ScannerGui scannerGui;
    private static AuthorizationController authorizationController;
    private static GameController gameController;
    private static GameplayController gameplayController;
    private static String token;
    private static Color color;

    public Client(UserInterface ui, DecisionMaker maker, String host, int port) {
        init(ui, maker, host, port);
    }

    public Client() {
        init();
        scannerGui = new ScannerGui();
    }

    public static void main(String[] args) throws IOException {
        new Client();
        token = authorizationController.authorizeClient();
        while (!socket.isClosed()) {
            try {
                color = gameController.joinToGame(token);
                gameplayController.processingGame(token, color);
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
        gameplayController = new GameplayController(streamConnector, userInterface, decisionMaker);
        authorizationController = new AuthorizationController(streamConnector, userInterface, decisionMaker);
    }

    public static void init() {
        UserInterfaceType userInterfaceType = UserInterfaceType.TERMINAL;
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            Properties property = new Properties();
            property.load(fis);

            host = property.getProperty("client.host");
            port = Integer.parseInt(property.getProperty("client.port"));
            userInterfaceType = UserInterfaceType.valueOf(property.getProperty("client.GUI").toUpperCase());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScannerGui scannerGui = new ScannerGui();
        switch (userInterfaceType) {
            case SWING -> init(new DisplayGui(scannerGui), new DecisionMakerGui(scannerGui), host, port);
            default -> init(new Display(), new DecisionMakerTerminal(new Scanner(System.in)), host, port);
        }
    }
}