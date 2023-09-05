package io.deeplay.intership.dao.file;

import io.deeplay.intership.dao.UserDao;
import io.deeplay.intership.exception.ErrorCode;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.User;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Optional;


public class UserDaoImpl implements UserDao {
    private static final String CREDENTIALS_FILE_NAME = "credentials.txt";
    private static final String AUTHORIZED_FILE_NAME = "auth.txt";
    private final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public void addUser(User user) throws ServerException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE_NAME, true))) {
            writer.write(user.login() + ":" + user.passwordHash());
            writer.newLine();
            logger.debug("Login and password have been successfully written to the file.");
        } catch (IOException ex) {
            logger.debug("Error writing username and password: " + ex.getMessage());
            throw new ServerException(ErrorCode.SERVER_EXCEPTION);
        }
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(login)) {
                    logger.debug("User was found");
                    return Optional.of(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException ex) {
            logger.debug("Login search error: " + ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void authorizeUser(User user, String token) throws ServerException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AUTHORIZED_FILE_NAME, true))) {
            writer.write(token + ":" + user.login());
            writer.newLine();
            logger.debug("Token and login have been successfully written to the file.");
        } catch (IOException ex) {
            logger.debug("Error authorize user by token: " + ex.getMessage());
            throw new ServerException(ErrorCode.SERVER_EXCEPTION);
        }
    }

    @Override
    public User getUserByToken(String token) throws ServerException {
        try (BufferedReader reader = new BufferedReader(new FileReader(AUTHORIZED_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(token)) {
                    logger.debug("User was found by token");
                    return getUserByLogin(parts[1])
                            .orElseThrow(() -> new ServerException(ErrorCode.NOT_FOUND_LOGIN));
                }
            }
        } catch (IOException ex) {
            throw new ServerException(ErrorCode.SERVER_EXCEPTION);
        }
        throw new ServerException(ErrorCode.NOT_AUTHORIZED);
    }

    @Override
    public void removeAuth(String token) throws ServerException {
        final String stub = "temp.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(AUTHORIZED_FILE_NAME));
             BufferedWriter writer = new BufferedWriter(new FileWriter(stub))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.equals(token)) {
                    writer.write(currentLine);
                }
            }

            File inputFile = new File(AUTHORIZED_FILE_NAME);
            new File(stub).renameTo(inputFile);
        } catch (IOException e) {
            throw new ServerException(ErrorCode.SERVER_EXCEPTION);
        }
    }
}
