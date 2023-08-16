package io.deeplay.intership.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.deeplay.intership.dto.RequestType;
import io.deeplay.intership.dto.request.CreateGameDtoRequest;
import io.deeplay.intership.model.Color;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConverterTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Converter converter = mock(Converter.class);

    @Test
    public void testConstructor() {
        assertDoesNotThrow(Converter::new);
    }

    @Test
    public void testGetClassFromJson() throws JsonProcessingException {
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

        final String request = mapper.writeValueAsString(expected);
        when(converter.getClassFromJson(request, CreateGameDtoRequest.class)).thenReturn(expected);

        final var result = converter.getClassFromJson(request, CreateGameDtoRequest.class);
        assertAll(
                () -> assertEquals(expected.requestType(), result.requestType()),
                () -> assertEquals(expected.withBot(), result.withBot()),
                () -> assertEquals(expected.color(), result.color()),
                () -> assertEquals(expected.size(), result.size()),
                () -> assertEquals(expected.token(), result.token())
        );
    }

    @Test
    public void testObjectToJson() throws JsonProcessingException {
        final RequestType requestType = RequestType.CREATE_GAME;
        final boolean withBot = false;
        final String color = Color.WHITE.name();
        final int gameFieldSize = 9;
        final String token = UUID.randomUUID().toString();

        final CreateGameDtoRequest object = new CreateGameDtoRequest(
                requestType,
                withBot,
                color,
                gameFieldSize,
                token
        );

        String expected = mapper.writeValueAsString(object);
        when(converter.objectToJson(expected)).thenReturn(expected);

        assertDoesNotThrow(() -> converter.objectToJson(object));
    }
}
