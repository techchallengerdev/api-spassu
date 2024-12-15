package com.br.spassu.api.infrastructure.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseWrapper<T> {
    private String message;
    private T data;
}
