package br.edu.catolica.customer_ms.dto;

import br.edu.catolica.customer_ms.enums.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderResponseDTO(
        String orderCode,
        List<ItemOrderDTO> items,
        String sellerName,
        BigDecimal amount,
        OrderStatus status
) {
}
