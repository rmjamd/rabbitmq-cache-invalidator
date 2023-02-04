package com.ramij.CacheInvalidator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ObjectMapperUtil<Type> {
    private static final ObjectMapper mapper = new ObjectMapper();

    public byte[] serialize(Type t) throws JsonProcessingException {
        return mapper.writeValueAsString(t).getBytes(StandardCharsets.UTF_8);
    }

    public Type deserialize(byte[] bytes, Class<Type> type) throws IOException {
        return mapper.readValue(bytes, type);
    }

}
