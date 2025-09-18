package com.sura.cliente.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JsonSerializer <T> implements Serializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            log.info("Object has been serialized successfully: {}", json);
            return json.getBytes(StandardCharsets.UTF_8);
        } catch (Exception exception) {
            throw new SerializationException("Error serializing object: ", exception);
        }
    }
}
