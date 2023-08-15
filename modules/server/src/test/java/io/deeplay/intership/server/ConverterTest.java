package io.deeplay.intership.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.dto.request.CreateGameDtoRequest;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testConstructor() {
        assertDoesNotThrow(Converter::new);
    }

    @Test
    public void testGetClassFromJson() throws JsonProcessingException {
        final Converter converter = new Converter();
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = false;
        final String color = Color.WHITE.name();
        final int gameFieldSize = 9;
        final String token = UUID.randomUUID().toString();

        final CreateGameDtoRequest expected = new CreateGameDtoRequest(
                requestType,
                withBot,
                color,
                gameFieldSize,
                token
        );

        String request = mapper.writeValueAsString(expected);
        var actual = converter.getClassFromJson(request, CreateGameDtoRequest.class);

        assertAll(
                () -> assertEquals(expected.requestType(), actual.requestType()),
                () -> assertEquals(expected.withBot(), actual.withBot()),
                () -> assertEquals(expected.color(), actual.color()),
                () -> assertEquals(expected.size(), actual.size()),
                () -> assertEquals(expected.token(), actual.token())
        );
    }

    @Test
    public void testObjectToJson() throws JsonProcessingException {
        final Converter converter = new Converter();
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = false;
        final String color = Color.WHITE.name();
        final int gameFieldSize = 9;
        final String token = UUID.randomUUID().toString();

        final CreateGameDtoRequest expected = new CreateGameDtoRequest(
                requestType,
                withBot,
                color,
                gameFieldSize,
                token
        );

        String request = mapper.writeValueAsString(expected);
        var actual = converter.objectToJson(expected);

        assertEquals(request, actual);
    }
}
