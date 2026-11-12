package com.edu.catolica.seller_ms.repository;

import com.edu.catolica.seller_ms.modal.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsByOrderCode(String orderCode);
    Optional<Order> findByOrderCode (String orderCode);
}
