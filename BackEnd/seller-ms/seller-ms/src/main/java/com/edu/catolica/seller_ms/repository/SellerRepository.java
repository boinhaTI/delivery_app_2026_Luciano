package com.edu.catolica.seller_ms.repository;

import com.edu.catolica.seller_ms.dto.SellerProductsDTO;
import com.edu.catolica.seller_ms.modal.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

}
