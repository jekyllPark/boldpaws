package com.dangdang.boldpaws.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
