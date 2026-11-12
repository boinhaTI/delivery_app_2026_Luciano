package com.edu.catolica.seller_ms.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderCreatedEventDTO(
        Long sellerId,
        Long customerId,
        String orderCode,
        String customerName,
        String customerEmail,
        List<ItemResquestDTO> items
) {
}
