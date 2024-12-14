package com.br.spassu.api.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private int status;
    private String type;
    private String title;
    private String detail;
    private LocalDateTime timestamp;

    @Builder.Default
    private List<String> errors = new ArrayList<>();
    private List<ValidationError> validationErrors;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationError {
        private String field;
        private String message;

        public static ValidationError of(String field, String message) {
            return ValidationError.builder()
                    .field(field)
                    .message(message)
                    .build();
        }
    }

    public static ApiError of(HttpStatus status, String type, String title, String detail) {
        return ApiError.builder()
                .status(status.value())
                .type(type)
                .title(title)
                .detail(detail)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public ApiError addValidationError(String field, String message) {
        if (this.validationErrors == null) {
            this.validationErrors = new ArrayList<>();
        }
        this.validationErrors.add(ValidationError.of(field, message));
        return this;
    }

    public ApiError addError(String error) {
        this.errors.add(error);
        return this;
    }
}