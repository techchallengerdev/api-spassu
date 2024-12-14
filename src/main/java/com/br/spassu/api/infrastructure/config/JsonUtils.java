package com.br.spassu.api.infrastructure.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private JsonUtils() {
    }

    public static String formatJsonForFrontend(String message, Object data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.createObjectNode()
                    .put("message", message)
                    .set("response", objectMapper.valueToTree(data));

            return objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao formatar o JSON para o frontend", e);
        }
    }
}
