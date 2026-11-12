package br.edu.catolica.customer_ms.dto;

import java.math.BigDecimal;

public record ItemOrderDTO(
        ProductDTO productoDTO,
        Integer quantity,
        BigDecimal subAmount
) {
}
