package com.edu.catolica.seller_ms.service;

import com.edu.catolica.seller_ms.dto.ItemOrderDTO;
import com.edu.catolica.seller_ms.dto.OrderCreatedEventDTO;
import com.edu.catolica.seller_ms.dto.OrderRequestDTO;
import com.edu.catolica.seller_ms.dto.OrderStatusUpdateDTO;
import com.edu.catolica.seller_ms.enums.OrderStatus;
import com.edu.catolica.seller_ms.exception.ProductException;
import com.edu.catolica.seller_ms.exception.SellerException;
import com.edu.catolica.seller_ms.mapper.OrderMapper;
import com.edu.catolica.seller_ms.messenger.OrderSendResponseStatus;
import com.edu.catolica.seller_ms.modal.Order;
import com.edu.catolica.seller_ms.modal.Product;
import com.edu.catolica.seller_ms.modal.Seller;
import com.edu.catolica.seller_ms.repository.OrderRepository;
import com.edu.catolica.seller_ms.repository.ProductRepository;
import com.edu.catolica.seller_ms.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.edu.catolica.seller_ms.constants.ProductConstants.PRODUCT_MESSAGE_204;
import static com.edu.catolica.seller_ms.constants.SellerConstants.SELLER_MESSAGE_204;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final OrderSendResponseStatus orderSendResponseStatus;
    //private final OrderNotificationPublisher orderNotificationPublisher;

    @Transactional
    public void saveOrder(OrderCreatedEventDTO eventDTO) {

        if (eventDTO.orderCode() == null || eventDTO.orderCode().isBlank()) {
            log.error("Pedido recebido sem orderCode");
        }

        if (orderRepository.existsByOrderCode(eventDTO.orderCode())) {
            log.warn("m=saveOrder Pedido ja existente = {}", eventDTO.orderCode());
            return;
        }

        Order order = orderMapper.toEntity(eventDTO);
        Seller seller = findSellerById(order);


        List<ItemOrderDTO> items = itemsOrderDTO(order);
        BigDecimal amount = calcularTotalAmount(items);

        order.setAmount(amount);
        order.setOrderStatus(OrderStatus.ACCEPTED);

        orderRepository.save(order);

        log.info("Status na entidade Order: {}", order.getOrderStatus());
        var response = orderMapper.orderResponseDTO(order, seller, items);


        orderSendResponseStatus.sendResponseOrderStatus(response);

        if (order.getOrderStatus().equals(OrderStatus.ACCEPTED)) {
            //TODO
        }

    }

    private List<ItemOrderDTO> itemsOrderDTO(Order order) {
        return order.getItems()
                .stream()
                .map(itemOrder -> {
                    Product product = productRepository.findById(itemOrder.getProductId()).orElseThrow(
                            () -> {
                                log.warn("m=toEntity, product not found, id = {}", itemOrder.getProductId());
                                return new ProductException(PRODUCT_MESSAGE_204);
                            });
                    itemOrder.setOrder(order);
                    itemOrder.setSubAmount(product.getPrice().multiply(BigDecimal.valueOf(itemOrder.getQuantity())));
                    return orderMapper.itemOrderDTO(itemOrder, product);
                }).toList();

    }

    private BigDecimal calcularTotalAmount(List<ItemOrderDTO> items) {
        return items.stream()
                .map(ItemOrderDTO::subAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Seller findSellerById(Order order) {
        return sellerRepository.findById(order.getSellerId()).orElseThrow(
                () -> {
                    log.warn("m=findSellerById, seller not found to id = {}", order.getSellerId());
                    return new SellerException(SELLER_MESSAGE_204);
                });
    }
    public void updateOrderStatus(OrderStatusUpdateDTO orderStatusUpdateDTO){
        Order order = orderRepository.findByOrderCode(orderStatusUpdateDTO.orderCode())
                .orElseThrow(() ->{
                    log.warn("m=updateOrderStatus, order not found to order code = {} ", orderStatusUpdateDTO.orderCode());
                    //return new OrderNotFoundException(ORDER_MESSAGE_204);
                    return new RuntimeException("Depois vou fazer");
                });
        order.setOrderStatus(orderStatusUpdateDTO.status());
        orderRepository.save(order);

        orderSendResponseStatus.sendOrderStatusUpdated(orderStatusUpdateDTO);

    }
}
