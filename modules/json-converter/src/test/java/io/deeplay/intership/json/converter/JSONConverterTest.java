package io.deeplay.intership.json.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.deeplay.intership.dto.request.CreateGameDtoRequest;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JSONConverterTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final JSONConverter converter = new JSONConverter();

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new JSONConverter());
        assertDoesNotThrow(() -> new JSONConverter(new ObjectMapper()));
    }

    @Test
    public void testGetClassFromJson() throws JsonProcessingException {
        final boolean withBot = false;
        final String color = Color.WHITE.name();
        final int gameFieldSize = 9;
        final String token = UUID.randomUUID().toString();

        final CreateGameDtoRequest expected = new CreateGameDtoRequest(
                withBot,
                color,
                gameFieldSize,
                token
        );

        final String request = mapper.writeValueAsString(expected);
        final var result = converter.getObjectFromJson(request, CreateGameDtoRequest.class);

        assertAll(
                () -> assertEquals(expected.withBot, result.withBot),
                () -> assertEquals(expected.color, result.color),
                () -> assertEquals(expected.size, result.size),
                () -> assertEquals(expected.token, result.token)
        );
    }

    @Test
    public void testJsonToObject_Failure() throws JsonProcessingException {
        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final JSONConverter converter = new JSONConverter(objectMapper);

        when(objectMapper.readValue("", CreateGameDtoRequest.class))
                .thenThrow(new JsonProcessingException("") {
                });
        assertThrows(RuntimeException.class, () -> converter.getObjectFromJson("", CreateGameDtoRequest.class));
    }

    @Test
    public void testObjectToJson() throws JsonProcessingException {
        final boolean withBot = false;
        final String color = Color.WHITE.name();
        final int gameFieldSize = 9;
        final String token = UUID.randomUUID().toString();

        final CreateGameDtoRequest object = new CreateGameDtoRequest(
                withBot,
                color,
                gameFieldSize,
                token
        );

        String expected = mapper.writeValueAsString(object);

        assertEquals(expected, converter.getJsonFromObject(object));
    }


    @Test
    public void testObjectToJson_Failure() throws JsonProcessingException {
        final ObjectMapper objectMapper = mock(ObjectMapper.class);
        final JSONConverter jsonConverter = new JSONConverter(objectMapper);

        final boolean withBot = false;
        final String color = Color.WHITE.name();
        final int gameFieldSize = 9;
        final String token = UUID.randomUUID().toString();
        final CreateGameDtoRequest object = new CreateGameDtoRequest(
                withBot,
                color,
                gameFieldSize,
                token
        );

        when(objectMapper.writeValueAsString(object))
                .thenThrow(new JsonProcessingException("") {
                });

        assertThrows(RuntimeException.class, () -> jsonConverter.getJsonFromObject(object));
    }
}
