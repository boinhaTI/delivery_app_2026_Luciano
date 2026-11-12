package com.edu.catolica.seller_ms.mapper;//package com.edu.catolica.seller_ms.mapper;
//
//import com.edu.catolica.seller_ms.dto.ItemOrderDTO;
//import com.edu.catolica.seller_ms.dto.OrderCreatedEventDTO;
//import com.edu.catolica.seller_ms.dto.OrderResponseDTO;
//import com.edu.catolica.seller_ms.modal.ItemOrder;
//import com.edu.catolica.seller_ms.modal.Order;
//
//import com.edu.catolica.seller_ms.modal.Product;
//import com.edu.catolica.seller_ms.modal.Seller;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface OrderMapper {
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "amount", ignore = true)
//    @Mapping(target = "orderStatus", ignore = true)
//    Order toEntity (OrderCreatedEventDTO orderCreatedEventDTO);
//
//    @Mapping(target = "productoDTO", source = "product")
//    ItemOrderDTO toItemOrderDTO(ItemOrder itemOrder, Product product);
//
//    @Mapping(target = "sellerName", source = "seller.name")
//    @Mapping(target = "items", source = "items")
//    @Mapping(target = "orderStatus", source = "order.orderStatus")
//    OrderResponseDTO toResponseDTO(Order order, Seller seller, List<ItemOrderDTO> items);
//}

import com.edu.catolica.seller_ms.dto.ItemOrderDTO;
import com.edu.catolica.seller_ms.dto.OrderCreatedEventDTO;
import com.edu.catolica.seller_ms.dto.OrderResponseDTO;
import com.edu.catolica.seller_ms.dto.ProductDTO;
import com.edu.catolica.seller_ms.modal.ItemOrder;
import com.edu.catolica.seller_ms.modal.Order;
import com.edu.catolica.seller_ms.modal.Product;
import com.edu.catolica.seller_ms.modal.Seller;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public Order toEntity(OrderCreatedEventDTO eventDTO) {
        if (eventDTO.items() == null || eventDTO.items().isEmpty()) {
            throw new IllegalArgumentException(
                    "O evento não possui itens: " + eventDTO.orderCode()
            );
        }

        Order order = Order.builder()
                .orderCode(eventDTO.orderCode())
                .customerId(eventDTO.customerId())
                .sellerId(eventDTO.sellerId())
                .build();

        List<ItemOrder> items = eventDTO.items().stream()
                .map(dto -> ItemOrder.builder()
                        .productId(dto.productId())
                        .quantity(dto.quantity())
                        .order(order)
                        .build())
                .toList();

        order.setItems(items);

        System.out.println(
                "Itens mapeados para o pedido " + order.getOrderCode()
                        + ": " + order.getItems().size()
        );

        return order;
    }

    public ItemOrderDTO itemOrderDTO(ItemOrder itemOrder, Product product) {
        ProductDTO productDTO = new ProductDTO(
                product.getId(),
                product.getDescription(),
                product.getPrice(),
                product.getSellerId()
        );
        return new ItemOrderDTO(
                productDTO,
                itemOrder.getQuantity(),
                itemOrder.getSubAmount()
        );
    }

    public OrderResponseDTO orderResponseDTO(Order order, Seller seller, List<ItemOrderDTO> items) {
        return new OrderResponseDTO(
                order.getOrderCode(),
                items,
                seller.getName(),
                order.getAmount(),
                order.getOrderStatus()
        );
    }

}