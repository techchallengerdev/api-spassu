package com.br.spassu.api.infrastructure.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ErrorResponse {
    private int status;
    private String type;
    private String title;
    private String detail;
    private LocalDateTime timestamp;
    private List<String> errors;
    private Map<String, String> validationErrors;
}
