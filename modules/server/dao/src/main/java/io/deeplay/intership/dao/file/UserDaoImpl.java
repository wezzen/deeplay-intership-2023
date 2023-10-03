package io.deeplay.intership.dao.file;

import io.deeplay.intership.dao.UserDao;
import io.deeplay.intership.exception.ServerErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.User;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {
    private static final String CREDENTIALS_FILE_NAME = "credentials.txt";
    private final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public void addUser(User user) throws ServerException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE_NAME, true))) {
            writer.write(user.login() + ":" + user.passwordHash());
            writer.newLine();
            logger.debug("Login and password have been successfully written to the file.");
        } catch (IOException ex) {
            logger.debug("Error writing username and password: " + ex.getMessage());
            throw new ServerException(ServerErrorCode.SERVER_EXCEPTION);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServerException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE_NAME))) {
            List<User> users = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                users.add(new User(parts[0], parts[1]));
            }
            return users;
        } catch (IOException ex) {
            logger.debug("Can't get all users");
            throw new ServerException(ServerErrorCode.SERVER_EXCEPTION);
        }
    }
}
