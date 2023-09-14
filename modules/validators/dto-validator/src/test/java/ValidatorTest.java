import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.dto.request.*;
import io.deeplay.intership.dto.request.authorization.LoginDtoRequest;
import io.deeplay.intership.dto.request.authorization.LogoutDtoRequest;
import io.deeplay.intership.dto.request.authorization.RegistrationDtoRequest;
import io.deeplay.intership.dto.request.game.CreateGameDtoRequest;
import io.deeplay.intership.dto.request.game.JoinGameDtoRequest;
import io.deeplay.intership.dto.request.gameplay.FinishGameDtoRequest;
import io.deeplay.intership.dto.validator.Validator;
import io.deeplay.intership.exception.ServerException;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {
    private final Validator validator = new Validator();

    @Test
    public void testSuccessValidationCreateGame1() {
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertDoesNotThrow(() -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testSuccessValidationCreateGame2() {
        final boolean withBot = true;
        final String color = Color.BLACK.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertDoesNotThrow(() -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testSuccessValidationCreateGame3() {
        final boolean withBot = true;
        final String color = Color.EMPTY.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertDoesNotThrow(() -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testSuccessValidationCreateGame4() {
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 13;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertDoesNotThrow(() -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testSuccessValidationCreateGame5() {
        final boolean withBot = false;
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertDoesNotThrow(() -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testFailureValidationCreateGame1() {
        final boolean withBot = true;
        final String color = null;
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertThrows(ServerException.class, () -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testFailureValidationCreateGame2() {
        final boolean withBot = true;
        final String color = "";
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertThrows(ServerException.class, () -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testFailureValidationCreateGame3() {
        final boolean withBot = true;
        final String color = "           ";
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertThrows(ServerException.class, () -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testFailureValidationCreateGame4() {
        final boolean withBot = true;
        final String color = Color.WHITE.name() + "   ";
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertThrows(ServerException.class, () -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testFailureValidationCreateGame5() {
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 8;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertThrows(ServerException.class, () -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testFailureValidationCreateGame6() {
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 0;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertThrows(ServerException.class, () -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testFailureValidationCreateGame7() {
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 21;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertThrows(ServerException.class, () -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testFailureValidationCreateGame8() {
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = null;
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                token);

        assertAll(
                () -> assertThrows(ServerException.class, () -> validator.validationCreateGameDto(dtoRequest))
        );
    }


    @Test
    public void testFailureValidationCreateGame9() {
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                withBot,
                color,
                size,
                "         ");

        assertAll(
                () -> assertThrows(ServerException.class, () -> validator.validationCreateGameDto(dtoRequest))
        );
    }

    @Test
    public void testSuccessValidationFinishGameDto1() {
        final String gameId = UUID.randomUUID().toString();
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(gameId);

        assertDoesNotThrow(() -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationFinishGameDto2() {
        final String gameId = "fisaogfdgfd1234";
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(gameId);

        assertDoesNotThrow(() -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationFinishGameDto1() {
        final String gameId = null;
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(gameId);

        assertThrows(ServerException.class, () -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationFinishGameDto2() {
        final String gameId = UUID.randomUUID().toString() + " ";
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(gameId);

        assertThrows(ServerException.class, () -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationFinishGameDto3() {
        final String gameId = "";
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(gameId);

        assertThrows(ServerException.class, () -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationFinishGameDto4() {
        final String gameId = "  ";
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(gameId);

        assertThrows(ServerException.class, () -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationFinishGameDto5() {
        final String gameId = "$%^ &*()@!#";
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(gameId);

        assertThrows(ServerException.class, () -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationJoinGameDto1() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertDoesNotThrow(() -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationJoinGameDto2() {
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertDoesNotThrow(() -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto1() {
        final String gameId = null;
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto2() {
        final String gameId = "";
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto3() {
        final String gameId = "     ";
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto4() {
        final String gameId = UUID.randomUUID().toString() + " ";
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto5() {
        final String gameId = UUID.randomUUID() + "!@#$ %^&*";
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto6() {
        final String gameId = UUID.randomUUID().toString();
        final String token = null;
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto7() {
        final String gameId = UUID.randomUUID().toString();
        final String token = "          ";
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto8() {
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString() + "  ";
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto9() {
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID() + "!@#*&$# %";
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testSuccessLoginDto1() {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = UUID.randomUUID().toString();
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                passwordHash);

        assertDoesNotThrow(() -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testSuccessLoginDto2() {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "123456789";
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                passwordHash);

        assertDoesNotThrow(() -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testSuccessLoginDto3() {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "qwertyuiopasdfghjklzxcvbnm";
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                passwordHash);

        assertDoesNotThrow(() -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testSuccessLoginDto4() {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "!@#$%^&*()[];':<>?:{}";
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                passwordHash);

        assertDoesNotThrow(() -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto1() {
        final String login = null;
        final String passwordHash = UUID.randomUUID().toString();
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto2() {
        final String login = "   ";
        final String passwordHash = UUID.randomUUID().toString();
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto3() {
        final String login = UUID.randomUUID().toString() + "  ";
        final String passwordHash = UUID.randomUUID().toString();
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto4() {
        final String login = UUID.randomUUID() + "{}!@#$$ ";
        final String passwordHash = UUID.randomUUID().toString();
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto5() {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = null;
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto6() {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "";
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto7() {
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "   ";
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationLogoutDto1() {
        final String token = UUID.randomUUID().toString();
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(token);

        assertDoesNotThrow(() -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationLogoutDto2() {
        final String token = "jfeiuerfids";
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(token);

        assertDoesNotThrow(() -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testFailureValidationLogoutDto1() {
        final String token = null;
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(token);

        assertThrows(ServerException.class, () -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testFailureValidationLogoutDto2() {
        final String token = "";
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(token);

        assertThrows(ServerException.class, () -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testFailureValidationLogoutDto3() {
        final String token = "       ";
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(token);

        assertThrows(ServerException.class, () -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testFailureValidationLogoutDto4() {
        final String token = UUID.randomUUID().toString() + " ";
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(token);

        assertThrows(ServerException.class, () -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testFailureValidationLogoutDto5() {
        final String token = UUID.randomUUID() + "! @#$%&*()";
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(token);

        assertThrows(ServerException.class, () -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationTurnDto() {
        final String color = Color.WHITE.name();
        final int row = 1;
        final int column = 1;
        final String token = UUID.randomUUID().toString();
        final TurnDtoRequest dtoRequest = new TurnDtoRequest(
                color,
                row,
                column,
                token);

        assertDoesNotThrow(() -> validator.validationTurnDto(dtoRequest));
    }

    @Test
    public void testFailureValidationTurnDto() {
        final String color = Color.WHITE.name();
        final int row = 100;
        final int column = -1;
        final String token = UUID.randomUUID().toString();
        final TurnDtoRequest dtoRequest = new TurnDtoRequest(
                color,
                row,
                column,
                token);

        assertThrows(ServerException.class, () -> validator.validationTurnDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationPassDto() {
        final String token = UUID.randomUUID().toString();
        final PassDtoRequest dtoRequest = new PassDtoRequest(token);

        assertDoesNotThrow(() -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testFailureValidationPassDto1() {
        final String token = null;
        final PassDtoRequest dtoRequest = new PassDtoRequest(token);

        assertThrows(ServerException.class, () -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testFailureValidationPassDto2() {
        final String token = "";
        final PassDtoRequest dtoRequest = new PassDtoRequest(token);

        assertThrows(ServerException.class, () -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testFailureValidationPassDto3() {
        final String token = "         ";
        final PassDtoRequest dtoRequest = new PassDtoRequest(token);

        assertThrows(ServerException.class, () -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testFailureValidationPassDto4() {
        final String token = "!@#$%^& *()";
        final PassDtoRequest dtoRequest = new PassDtoRequest(token);

        assertThrows(ServerException.class, () -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testFailureValidationPassDto5() {
        final String token = UUID.randomUUID().toString() + " ";
        final PassDtoRequest dtoRequest = new PassDtoRequest(token);

        assertThrows(ServerException.class, () -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testFailureValidationPassDto6() {
        final String token = UUID.randomUUID() + "{}:#!$ ^&?></.,";
        final PassDtoRequest dtoRequest = new PassDtoRequest(token);

        assertThrows(ServerException.class, () -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testSuccessRegistrationDto() {
        final String login = "admin123";
        final String passwordHash = "fmodnoaial1342er32985";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                login,
                passwordHash);

        assertDoesNotThrow(() -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto1() {
        final String login = null;
        final String passwordHash = "fmodnoaial1342er32985";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto2() {
        final String login = "";
        final String passwordHash = "fmodnoaial1342er32985";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto3() {
        final String login = "     ";
        final String passwordHash = "fmodnoaial1342er32985";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto4() {
        final String login = " admin123 ";
        final String passwordHash = "fmodnoaial1342er32985";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto5() {
        final String login = "admin123";
        final String passwordHash = null;
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto6() {
        final String login = "admin123";
        final String passwordHash = "";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto7() {
        final String login = "admin123";
        final String passwordHash = "        ";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

}
