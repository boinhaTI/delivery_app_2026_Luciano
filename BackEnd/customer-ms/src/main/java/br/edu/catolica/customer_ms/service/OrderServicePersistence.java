package br.edu.catolica.customer_ms.service;

import br.edu.catolica.customer_ms.domain.Order;
import br.edu.catolica.customer_ms.dto.OrderRequestDTO;
import br.edu.catolica.customer_ms.enums.OrderStatus;
import br.edu.catolica.customer_ms.exception.SaveOrderException;
import br.edu.catolica.customer_ms.mapper.OrderMapper;
import br.edu.catolica.customer_ms.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServicePersistence {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public void saveOrder(OrderRequestDTO orderRequestDTO) {
        try {
            Order order = orderMapper.toEntity(orderRequestDTO);
            order.setStatus(OrderStatus.ANALYZE);

            orderRepository.save(order);
        } catch (Exception ex) {
            log.error("m=saveOrder, failed to try save order, order code = {} ", orderRequestDTO.orderCode());
            throw new SaveOrderException("m=saveOrder, failed to try save order" + ex.getMessage());
        }
    }

    public void updateOrderToFailed(String orderCode) {
        orderRepository.findByOrderCode(orderCode).ifPresentOrElse(order -> {
            order.setStatus(OrderStatus.FAILED_TO_SEND);
            orderRepository.save(order);
        },
                () -> log.warn("m=updateOrderToFailded," + " order not found to order code = {}", orderCode));
    }
}
