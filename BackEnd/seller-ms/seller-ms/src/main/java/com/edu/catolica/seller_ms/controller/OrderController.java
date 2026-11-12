package com.edu.catolica.seller_ms.controller;

import com.edu.catolica.seller_ms.dto.OrderStatusUpdateDTO;
import com.edu.catolica.seller_ms.dto.ResponseDTO;
import com.edu.catolica.seller_ms.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/status")
    public ResponseEntity<ResponseDTO> updateStatus(@RequestBody @Valid OrderStatusUpdateDTO orderStatusUpdateDTO) {
        orderService.updateOrderStatus(orderStatusUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Atualizado com sucesso", HttpStatus.OK.value()));
    }
}
