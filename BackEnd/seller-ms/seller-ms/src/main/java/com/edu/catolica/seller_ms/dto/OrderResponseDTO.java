package com.edu.catolica.seller_ms.dto;


import com.edu.catolica.seller_ms.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderResponseDTO(
        @NotBlank
        String orderCode,

        @JsonProperty("items")
        List<ItemOrderDTO> items,

        @NotBlank
        @JsonProperty("Estabelecimento")
        String sellerName,

        @Positive
        @JsonProperty("Total do produto")
        BigDecimal amount,

        @NotNull
        @JsonProperty("Status do pedido")
        OrderStatus orderStatus
) {
}
