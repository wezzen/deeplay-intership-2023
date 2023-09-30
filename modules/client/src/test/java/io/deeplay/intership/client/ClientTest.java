package io.deeplay.intership.client;

import io.deeplay.intership.decision.maker.DecisionMaker;
import io.deeplay.intership.ui.UserInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

class ClientTest {
    private static final String CONFIG_PATH = "src/main/resources/config.properties";
    private static String host;
    private static int port;
    private static UserInterface userInterface;
    private static DecisionMaker decisionMaker;

    @BeforeAll
    public static void setProperties() {
        userInterface = mock(UserInterface.class);
        decisionMaker = mock(DecisionMaker.class);
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            Properties property = new Properties();
            property.load(fis);
            host = property.getProperty("client.host");
            port = Integer.parseInt(property.getProperty("client.port"));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void creatingInstanceTest() {
        assertDoesNotThrow(() -> new Client());
    }
}