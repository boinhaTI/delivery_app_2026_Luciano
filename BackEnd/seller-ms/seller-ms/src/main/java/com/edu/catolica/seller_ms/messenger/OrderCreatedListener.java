package com.edu.catolica.seller_ms.messenger;

import com.edu.catolica.seller_ms.dto.OrderCreatedEventDTO;
import com.edu.catolica.seller_ms.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.edu.catolica.seller_ms.constants.TopicConstants.ORDER_CREATED;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedListener {

    private final OrderService orderService;

    @KafkaListener(topics = ORDER_CREATED, groupId = "${spring.kafka.consumer.group-id}")
    public void saveAndResponseOrder(OrderCreatedEventDTO eventDTO) {
        log.info("Evento recebido do Kafka: {}", eventDTO);
        orderService.saveOrder(eventDTO);
        log.info("Consumindo topico de order-created, evento = {}", eventDTO);
    }
}
