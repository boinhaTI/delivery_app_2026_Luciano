package br.edu.catolica.customer_ms.service;

import br.edu.catolica.customer_ms.domain.Customer;
import br.edu.catolica.customer_ms.domain.ItemOrder;
import br.edu.catolica.customer_ms.domain.Order;
import br.edu.catolica.customer_ms.dto.*;
import br.edu.catolica.customer_ms.event.OrderEventPublisher;
import br.edu.catolica.customer_ms.exception.CustomerException;
import br.edu.catolica.customer_ms.exception.OrderNotFoundException;
import br.edu.catolica.customer_ms.repository.CustomerRepository;
import br.edu.catolica.customer_ms.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventService {

    private static final String ORDER_MESSAGE_204 = "";
    private final OrderServicePersistence orderServicePersistence;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderEventPublisher orderEventPublisher;

    public void send(OrderRequestDTO orderRequestDTO) {

        Customer customer = customerRepository.findById(orderRequestDTO.customerId()).orElseThrow(
                () -> {
                    log.warn("m=send, customer not found to id = {}", orderRequestDTO.customerId());
                    return new CustomerException("Customer não encontrado");
                });

        OrderRequestDTO orderWithCode = withOrderCode(orderRequestDTO);

        orderServicePersistence.saveOrder(orderWithCode);

        OrderCreatedEventDTO eventDTO = OrderCreatedEventDTO.builder()
                .customerEmail(customer.getEmail())
                .customerId(customer.getId())
                .customerName(customer.getName())
                .items(orderRequestDTO.items())
                .orderCode(orderWithCode.orderCode())
                .sellerId(orderWithCode.sellerId())
                .build();

        try {
            log.info(
                    "Publicando pedido no Kafka, orderCode={}",
                    eventDTO.orderCode()
            );
            log.info("Order code salvo: {}", orderWithCode.orderCode());
            log.info("Evento que será publicado: {}", eventDTO);

            orderEventPublisher.sendOrderRequestevent(eventDTO);

        } catch (Exception ex) {
            log.error("m=send, fail try send order to topic, order code = {}", eventDTO.orderCode());
            orderServicePersistence.updateOrderToFailed(eventDTO.orderCode());
            throw new RuntimeException(ex);
        }
    }

    private OrderRequestDTO withOrderCode(OrderRequestDTO orderRequestDTO) {
        if (Strings.isNotBlank(orderRequestDTO.orderCode())) {
            return orderRequestDTO;
        }

        String orderCode = UUID.randomUUID().toString();
        return new OrderRequestDTO(orderRequestDTO.sellerId(),
                orderRequestDTO.customerId(),
                orderCode,
                orderRequestDTO.items());
    }

    public void applyResponse(OrderResponseDTO orderResponseDTO) {

        if (StringUtils.isBlank(orderResponseDTO.orderCode())) {
            log.error("m=applyResponse, invalid order code");
            return;
        }
        Order order = findOrderByCode(orderResponseDTO.orderCode());

        List<ItemOrder> items = orderResponseDTO.items()
                .stream()
                .map(itemDTO -> toEntity(itemDTO, order))
                .toList();


    }
    public void applyOrderStatusUpdate(OrderStatusUpdateDTO orderStatusUpdateDTO){

        if(orderStatusUpdateDTO.orderCode().isBlank()){
            log.error("m=applyOrderStatusUpdate, invalid order code");
            return;
        }

        Order order = orderRepository.findByOrderCode(orderStatusUpdateDTO.orderCode())
                .orElseThrow(() -> {
                    log.warn("m=applyOrderStatusUpdate, order not found to order code = {} ",orderStatusUpdateDTO.orderCode());
                    return new OrderNotFoundException(ORDER_MESSAGE_204);
                });
        order.setStatus(orderStatusUpdateDTO.status());
        orderRepository.save(order);

    }


    public Order findOrderByCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode).orElseThrow(
                () -> {
                    log.warn("m=findOrderByCode order not found by order code = {}", orderCode);
                    return new OrderNotFoundException("Pedido não encontrado!");
                });
    }

    private ItemOrder toEntity(ItemOrderDTO itemOrderDTO, Order order) {
        return ItemOrder.builder()
                .id(itemOrderDTO.productoDTO().id())
                .quantity(itemOrderDTO.quantity())
                .subAmount(itemOrderDTO.subAmount())
                .order(order)
                .build();
    }
}
