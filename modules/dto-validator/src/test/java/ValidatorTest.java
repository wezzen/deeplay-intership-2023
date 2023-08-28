import io.deeplay.intership.dto.request.RequestType;
import io.deeplay.intership.dto.request.*;
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = Color.BLACK.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = Color.EMPTY.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 13;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = false;
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = null;
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = "";
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = "           ";
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = Color.WHITE.name() + "   ";
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 8;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 0;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 21;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = null;
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = true;
        final String color = Color.WHITE.name();
        final int size = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest dtoRequest = new CreateGameDtoRequest(
                requestType,
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
        final RequestType requestType = RequestType.FINISH_GAME;
        final String gameId = UUID.randomUUID().toString();
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(
                requestType,
                gameId);

        assertDoesNotThrow(() -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationFinishGameDto2() {
        final RequestType requestType = RequestType.FINISH_GAME;
        final String gameId = "fisaogfdgfd1234";
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(
                requestType,
                gameId);

        assertDoesNotThrow(() -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationFinishGameDto1() {
        final RequestType requestType = RequestType.FINISH_GAME;
        final String gameId = null;
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(
                requestType,
                gameId);

        assertThrows(ServerException.class, () -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationFinishGameDto2() {
        final RequestType requestType = RequestType.FINISH_GAME;
        final String gameId = UUID.randomUUID().toString() + " ";
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(
                requestType,
                gameId);

        assertThrows(ServerException.class, () -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationFinishGameDto3() {
        final RequestType requestType = RequestType.FINISH_GAME;
        final String gameId = "";
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(
                requestType,
                gameId);

        assertThrows(ServerException.class, () -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationFinishGameDto4() {
        final RequestType requestType = RequestType.FINISH_GAME;
        final String gameId = "  ";
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(
                requestType,
                gameId);

        assertThrows(ServerException.class, () -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationFinishGameDto5() {
        final RequestType requestType = RequestType.FINISH_GAME;
        final String gameId = "$%^ &*()@!#";
        final FinishGameDtoRequest dtoRequest = new FinishGameDtoRequest(
                requestType,
                gameId);

        assertThrows(ServerException.class, () -> validator.validationFinishGameDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationJoinGameDto1() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                requestType,
                gameId,
                token,
                color);

        assertDoesNotThrow(() -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationJoinGameDto2() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                requestType,
                gameId,
                token,
                color);

        assertDoesNotThrow(() -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto1() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String gameId = null;
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                requestType,
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto2() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String gameId = "";
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                requestType,
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto3() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String gameId = "     ";
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                requestType,
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto4() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String gameId = UUID.randomUUID().toString() + " ";
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                requestType,
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto5() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String gameId = UUID.randomUUID() + "!@#$ %^&*";
        final String token = UUID.randomUUID().toString();
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                requestType,
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto6() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String gameId = UUID.randomUUID().toString();
        final String token = null;
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                requestType,
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto7() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String gameId = UUID.randomUUID().toString();
        final String token = "          ";
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                requestType,
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto8() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID().toString() + "  ";
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                requestType,
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testFailureValidationJoinGameDto9() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String gameId = UUID.randomUUID().toString();
        final String token = UUID.randomUUID() + "!@#*&$# %";
        final String color = Color.WHITE.name();
        final JoinGameDtoRequest dtoRequest = new JoinGameDtoRequest(
                requestType,
                gameId,
                token,
                color);

        assertThrows(ServerException.class, () -> validator.validationJoinGameDto(dtoRequest));
    }

    @Test
    public void testSuccessLoginDto1() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = UUID.randomUUID().toString();
        final String passwordHash = UUID.randomUUID().toString();
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                requestType,
                login,
                passwordHash);

        assertDoesNotThrow(() -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testSuccessLoginDto2() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "123456789";
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                requestType,
                login,
                passwordHash);

        assertDoesNotThrow(() -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testSuccessLoginDto3() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "qwertyuiopasdfghjklzxcvbnm";
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                requestType,
                login,
                passwordHash);

        assertDoesNotThrow(() -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testSuccessLoginDto4() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "!@#$%^&*()[];':<>?:{}";
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                requestType,
                login,
                passwordHash);

        assertDoesNotThrow(() -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto1() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = null;
        final String passwordHash = UUID.randomUUID().toString();
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto2() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = "   ";
        final String passwordHash = UUID.randomUUID().toString();
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto3() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = UUID.randomUUID().toString() + "  ";
        final String passwordHash = UUID.randomUUID().toString();
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto4() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = UUID.randomUUID() + "{}!@#$$ ";
        final String passwordHash = UUID.randomUUID().toString();
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto5() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = UUID.randomUUID().toString();
        final String passwordHash = null;
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto6() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "";
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testFailureLoginDto7() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = UUID.randomUUID().toString();
        final String passwordHash = "   ";
        final LoginDtoRequest dtoRequest = new LoginDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationLoginDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationLogoutDto1() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = UUID.randomUUID().toString();
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(
                requestType,
                token);

        assertDoesNotThrow(() -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationLogoutDto2() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = "jfeiuerfids";
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(
                requestType,
                token);

        assertDoesNotThrow(() -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testFailureValidationLogoutDto1() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = null;
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(
                requestType,
                token);

        assertThrows(ServerException.class, () -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testFailureValidationLogoutDto2() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = "";
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(
                requestType,
                token);

        assertThrows(ServerException.class, () -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testFailureValidationLogoutDto3() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = "       ";
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(
                requestType,
                token);

        assertThrows(ServerException.class, () -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testFailureValidationLogoutDto4() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = UUID.randomUUID().toString() + " ";
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(
                requestType,
                token);

        assertThrows(ServerException.class, () -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testFailureValidationLogoutDto5() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = UUID.randomUUID() + "! @#$%&*()";
        final LogoutDtoRequest dtoRequest = new LogoutDtoRequest(
                requestType,
                token);

        assertThrows(ServerException.class, () -> validator.validationLogoutDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationTurnDto() {
        final RequestType requestType = RequestType.TURN;
        final String color = Color.WHITE.name();
        final int row = 1;
        final int column = 1;
        final String token = UUID.randomUUID().toString();
        final TurnDtoRequest dtoRequest = new TurnDtoRequest(
                RequestType.TURN,
                color,
                row,
                column,
                token);

        assertDoesNotThrow(() -> validator.validationTurnDto(dtoRequest));
    }

    @Test
    public void testFailureValidationTurnDto() {
        final RequestType requestType = RequestType.TURN;
        final String color = Color.WHITE.name();
        final int row = 100;
        final int column = -1;
        final String token = UUID.randomUUID().toString();
        final TurnDtoRequest dtoRequest = new TurnDtoRequest(
                RequestType.TURN,
                color,
                row,
                column,
                token);

        assertThrows(ServerException.class, () -> validator.validationTurnDto(dtoRequest));
    }

    @Test
    public void testSuccessValidationPassDto() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = UUID.randomUUID().toString();
        final PassDtoRequest dtoRequest = new PassDtoRequest(
                requestType,
                token);

        assertDoesNotThrow(() -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testFailureValidationPassDto1() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = null;
        final PassDtoRequest dtoRequest = new PassDtoRequest(
                requestType,
                token);

        assertThrows(ServerException.class, () -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testFailureValidationPassDto2() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = "";
        final PassDtoRequest dtoRequest = new PassDtoRequest(
                requestType,
                token);

        assertThrows(ServerException.class, () -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testFailureValidationPassDto3() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = "         ";
        final PassDtoRequest dtoRequest = new PassDtoRequest(
                requestType,
                token);

        assertThrows(ServerException.class, () -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testFailureValidationPassDto4() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = "!@#$%^& *()";
        final PassDtoRequest dtoRequest = new PassDtoRequest(
                requestType,
                token);

        assertThrows(ServerException.class, () -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testFailureValidationPassDto5() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = UUID.randomUUID().toString() + " ";
        final PassDtoRequest dtoRequest = new PassDtoRequest(
                requestType,
                token);

        assertThrows(ServerException.class, () -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testFailureValidationPassDto6() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String token = UUID.randomUUID() + "{}:#!$ ^&?></.,";
        final PassDtoRequest dtoRequest = new PassDtoRequest(
                requestType,
                token);

        assertThrows(ServerException.class, () -> validator.validationPassDto(dtoRequest));
    }

    @Test
    public void testSuccessRegistrationDto() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = "admin123";
        final String passwordHash = "fmodnoaial1342er32985";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                requestType,
                login,
                passwordHash);

        assertDoesNotThrow(() -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto1() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = null;
        final String passwordHash = "fmodnoaial1342er32985";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto2() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = "";
        final String passwordHash = "fmodnoaial1342er32985";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto3() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = "     ";
        final String passwordHash = "fmodnoaial1342er32985";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto4() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = " admin123 ";
        final String passwordHash = "fmodnoaial1342er32985";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto5() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = "admin123";
        final String passwordHash = null;
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto6() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = "admin123";
        final String passwordHash = "";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

    @Test
    public void testFailureRegistrationDto7() {
        final RequestType requestType = RequestType.JOIN_GAME;
        final String login = "admin123";
        final String passwordHash = "        ";
        final RegistrationDtoRequest dtoRequest = new RegistrationDtoRequest(
                requestType,
                login,
                passwordHash);

        assertThrows(ServerException.class, () -> validator.validationRegistrationDto(dtoRequest));
    }

}
