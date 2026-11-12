package br.edu.catolica.customer_ms.dto;

import java.math.BigDecimal;

public record OrderItemNotificationDTO(
        String productName,
        BigDecimal quantity,
        BigDecimal subAmount
) {
}
