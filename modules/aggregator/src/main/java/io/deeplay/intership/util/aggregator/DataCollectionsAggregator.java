package io.deeplay.intership.util.aggregator;

import io.deeplay.intership.game.GameSession;
import io.deeplay.intership.model.User;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public record DataCollectionsAggregator(
        ConcurrentMap<String, GameSession> idToGameSession,
        ConcurrentMap<String, User> tokenToUser,
        ConcurrentMap<String, String> playerToGame,
        ConcurrentMap<String, User> users) {
    private static final String CREDENTIALS_FILE_NAME = "credentials.txt";
    private static final Logger logger = Logger.getLogger(DataCollectionsAggregator.class);

    public DataCollectionsAggregator(
            ConcurrentMap<String, GameSession> idToGameSession,
            ConcurrentMap<String, User> tokenToUser,
            ConcurrentMap<String, String> playerToGame,
            ConcurrentMap<String, User> users) {
        this.idToGameSession = idToGameSession;
        this.tokenToUser = tokenToUser;
        this.playerToGame = playerToGame;
        this.users = users;
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                users.put(parts[0], new User(parts[0], parts[1]));
            }
        } catch (FileNotFoundException ex) {
            logger.debug("File with users data not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DataCollectionsAggregator() {
        this(new ConcurrentHashMap<>(), new ConcurrentHashMap<>(), new ConcurrentHashMap<>(), new ConcurrentHashMap<>());
    }
}
