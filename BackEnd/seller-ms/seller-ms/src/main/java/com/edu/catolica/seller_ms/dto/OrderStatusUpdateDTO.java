package com.edu.catolica.seller_ms.dto;

import com.edu.catolica.seller_ms.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateDTO(
        @NotBlank
        String orderCode,

        @NotNull
        OrderStatus status
) {
}
