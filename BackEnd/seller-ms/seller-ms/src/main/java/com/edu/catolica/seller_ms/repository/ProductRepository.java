package com.edu.catolica.seller_ms.repository;

import com.edu.catolica.seller_ms.modal.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findBySellerId(@Param("sellerId") Long sellerId);
}
