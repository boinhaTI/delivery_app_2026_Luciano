package br.edu.catolica.customer_ms.messager;

import br.edu.catolica.customer_ms.dto.OrderResponseDTO;
import br.edu.catolica.customer_ms.dto.OrderStatusUpdateDTO;
import br.edu.catolica.customer_ms.service.OrderEventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.lang.management.GarbageCollectorMXBean;

import static br.edu.catolica.customer_ms.constants.TopicConstants.ORDER_RESPONSE;

@Component
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = ORDER_RESPONSE, groupId = "${spring.kafka.consumer.group-id}")
public class OrderResponseListener {

    private final OrderEventService orderEventService;

    @KafkaHandler
    public void orderResponse(OrderResponseDTO orderResponseDTO) {

        try {
            orderEventService.applyResponse(orderResponseDTO);
        } catch (Exception e) {

            log.info("m=orderResponseListener, mensagem recebida no topico = {}", orderResponseDTO);
        }
    }

    public void updateStatus(OrderStatusUpdateDTO orderStatusUpdateDTO) {
        orderEventService.applyOrderStatusUpdate(orderStatusUpdateDTO);
    }
}
