package br.edu.catolica.customer_ms.event;

import br.edu.catolica.customer_ms.dto.OrderCreatedEventDTO;
import br.edu.catolica.customer_ms.exception.EventOrderException;
import br.edu.catolica.customer_ms.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static br.edu.catolica.customer_ms.constants.TopicConstants.ORDER_CREATED;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final KafkaProducerService kafkaProducerService;

    public void sendOrderRequestevent(OrderCreatedEventDTO eventDTO) {
        try {
            log.info("Tópico: {}", ORDER_CREATED);
            log.info("Chave Kafka (orderCode): '{}'", eventDTO.orderCode());
            log.info("Evento publicado: {}", eventDTO);

            kafkaProducerService.sendEvent(ORDER_CREATED, eventDTO.orderCode(), eventDTO);
        } catch (Exception ex) {
            log.error("Falha ao publicar evento do pedido", ex);
            throw new EventOrderException("Falha na comunicação dos serviços" + ex.getMessage());
        }
    }
}
