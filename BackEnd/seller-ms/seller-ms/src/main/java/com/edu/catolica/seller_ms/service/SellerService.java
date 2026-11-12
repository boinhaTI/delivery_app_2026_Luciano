package com.edu.catolica.seller_ms.service;

import com.edu.catolica.seller_ms.dto.SellerDTO;
import com.edu.catolica.seller_ms.dto.SellerProductsDTO;
import com.edu.catolica.seller_ms.exception.SellerException;
import com.edu.catolica.seller_ms.mapper.ProductMapper;
import com.edu.catolica.seller_ms.mapper.SellerMapper;
import com.edu.catolica.seller_ms.modal.Seller;
import com.edu.catolica.seller_ms.repository.ProductRepository;
import com.edu.catolica.seller_ms.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerService {

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public void save(SellerDTO sellerDTO) {
        try {
            Seller seller = sellerMapper.toEntity(sellerDTO);
            sellerRepository.save(seller);
        } catch (Exception e) {
            log.error("m=save, error to try save seller with name = {}", sellerDTO.name());
            throw new SellerException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public SellerProductsDTO sellerProductsDTO(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
                () -> {
                    log.warn("m=sellerProductsDTO, seller not found to id");
                    return new RuntimeException("Seller not found to id");
                });
        var products = productRepository.findBySellerId(sellerId);
        return new SellerProductsDTO(seller.getName(), productMapper.entityToDTOList(products));
    }
}
