package com.lny.nearby.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

public class JsonUtils {
    private static final ObjectMapper JSON = new ObjectMapper();

    public static Mono<String> write(Object value) {
        try {
            final String s = JSON.writeValueAsString(value);
            return Mono.just(s);
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }
}
