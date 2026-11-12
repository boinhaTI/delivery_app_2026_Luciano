package br.edu.catolica.customer_ms.dto;


import br.edu.catolica.customer_ms.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateDTO(
        @NotBlank
        String orderCode,

        @NotNull
        OrderStatus status
) {
}
