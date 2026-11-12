package br.edu.catolica.customer_ms.dto;

import lombok.Builder;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Builder
public record OrderRequestDTO(
        Long sellerId,
        Long customerId,
        String orderCode,
        List<ItemResquestDTO> items
) {
    public OrderRequestDTO(Long sellerId, Long customerId, List<ItemResquestDTO> items) {
        this(sellerId, customerId, UUID.randomUUID().toString(), items);
    }
}
