package io.deeplay.intership.dao.file;

import io.deeplay.intership.dao.GameDao;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import org.apache.log4j.Logger;

import java.io.*;

public class GameDaoImpl implements GameDao {
    private static final String ACTIVE_PLAYERS = "active_sessions.txt";
    private final Logger logger = Logger.getLogger(GameDaoImpl.class);

    @Override
    public void addActiveUser(String token, String gameId) throws ServerException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACTIVE_PLAYERS, true))) {
            writer.write(token + ":" + gameId);
            writer.newLine();
            logger.debug("Login and gameId have been successfully written to the file.");
        } catch (IOException ex) {
            throw new ServerException(ErrorCode.SERVER_EXCEPTION);
        }
    }

    @Override
    public String getGameIdByPlayerLogin(String token) throws ServerException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACTIVE_PLAYERS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(token)) {
                    logger.debug("User was found by token");
                    return parts[1];
                }
            }
        } catch (IOException ex) {
            throw new ServerException(ErrorCode.SERVER_EXCEPTION);
        }
        throw new ServerException(ErrorCode.GAME_NOT_FOUND);
    }
}
