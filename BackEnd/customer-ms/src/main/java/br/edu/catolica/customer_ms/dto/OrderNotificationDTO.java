package br.edu.catolica.customer_ms.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderNotificationDTO(

        String orderCode,
        String customerName,
        String customerEmail,
        String sellerName,
        List<OrderItemNotificationDTO> items,
        BigDecimal amount
) {
}
