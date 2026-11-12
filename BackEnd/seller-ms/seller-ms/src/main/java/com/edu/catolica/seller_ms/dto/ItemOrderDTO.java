package com.edu.catolica.seller_ms.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemOrderDTO(
        ProductDTO productoDTO,
        Integer quantity,
        BigDecimal subAmount
) {
}
