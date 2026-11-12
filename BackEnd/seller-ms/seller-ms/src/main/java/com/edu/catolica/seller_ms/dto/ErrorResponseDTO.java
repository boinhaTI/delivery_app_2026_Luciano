package com.edu.catolica.seller_ms.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponseDTO(
        String apiPath,
        Integer httpStatusCode,
        String message,
        LocalDateTime errorTime
) {
}
