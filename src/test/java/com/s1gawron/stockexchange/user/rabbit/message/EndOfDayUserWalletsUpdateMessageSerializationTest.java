package com.s1gawron.stockexchange.user.rabbit.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.s1gawron.stockexchange.shared.ObjectMapperCreator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.util.List;

class EndOfDayUserWalletsUpdateMessageSerializationTest {

    private final ObjectMapper mapper = ObjectMapperCreator.I.getMapper();

    @Test
    @SneakyThrows
    void shouldSerialize() {
        final Instant fixedInstant = LocalDateTime.parse("2022-04-13T10:59:15.994144").toInstant(OffsetDateTime.now().getOffset());
        final Clock clock = Clock.fixed(fixedInstant, ZoneId.systemDefault());
        final EndOfDayUserWalletsUpdateMessage message = EndOfDayUserWalletsUpdateMessage.create(List.of("test0", "test1", "test2", "test3"), clock);
        final String messageJsonResult = mapper.writeValueAsString(message);
        final String expectedMessageJsonResult = Files.readString(Path.of("src/test/resources/end-of-day-user-wallets-update-message.json"));

        final JsonNode expected = mapper.readTree(expectedMessageJsonResult);
        final JsonNode result = mapper.readTree(messageJsonResult);

        Assertions.assertEquals(expected, result);
    }

}