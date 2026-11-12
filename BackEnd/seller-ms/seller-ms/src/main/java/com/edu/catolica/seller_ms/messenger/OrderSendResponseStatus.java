package com.edu.catolica.seller_ms.messenger;

import com.edu.catolica.seller_ms.dto.OrderResponseDTO;
import com.edu.catolica.seller_ms.dto.OrderStatusUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.edu.catolica.seller_ms.constants.TopicConstants.ORDER_RESPONSE;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderSendResponseStatus {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void sendResponseOrderStatus(OrderResponseDTO orderResponseDTO) {

        try {
            kafkaTemplate.send(ORDER_RESPONSE, orderResponseDTO.orderCode(), orderResponseDTO);

        } catch (Exception e) {
            log.error("m=sindResponseOrderStatus, error to send event to order response topic");
            throw new RuntimeException();
        }
    }
    public void sendOrderStatusUpdated(OrderStatusUpdateDTO orderStatusUpdateDTO){
        try{
            kafkaTemplate.send(ORDER_RESPONSE, orderStatusUpdateDTO.orderCode(), orderStatusUpdateDTO);
        } catch (Exception e) {
            log.error("m=sendOrderStatusUpdated, error to try send update order status to order code = {} ", orderStatusUpdateDTO.orderCode() );
            //TODO:throw new OrderSendUpdateException(e.getMessage());
            throw new RuntimeException("Voltar para fazer o metodo de erro correto");
        }

    }
}
