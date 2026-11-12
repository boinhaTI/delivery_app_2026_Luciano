package com.edu.catolica.seller_ms.service;

import com.edu.catolica.seller_ms.cache.ProductCacheWriter;
import com.edu.catolica.seller_ms.constants.SellerConstants;
import com.edu.catolica.seller_ms.dto.ProductDTO;
import com.edu.catolica.seller_ms.exception.ProductException;
import com.edu.catolica.seller_ms.exception.SellerException;
import com.edu.catolica.seller_ms.mapper.ProductMapper;
import com.edu.catolica.seller_ms.modal.Product;
import com.edu.catolica.seller_ms.modal.Seller;
import com.edu.catolica.seller_ms.repository.ProductRepository;
import com.edu.catolica.seller_ms.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {


    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ProductMapper productMapper;
    private final ProductCacheWriter productCacheWriter;

    @Transactional
    public void save(ProductDTO productDTO) {
        Seller seller = findSellerById(productDTO.sellerId());
        try {
            Product product = productMapper.dtoToProduct(productDTO);
            product.setSellerId(seller.getId());
            Product productSaved = productRepository.save(product);
            productCacheWriter.save(productSaved);
        } catch (Exception ex) {
            log.error("m=save, error to try save product with description = {}" + " to seller with id = {}", productDTO.description(), productDTO.sellerId());
            throw new ProductException(ex.getMessage());
        }
    }
    private Seller findSellerById(Long id) {
        return sellerRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("m=findSellerById, seller not fount by id = {}", id);
                    return new SellerException(SellerConstants.SELLER_MESSAGE_204);
                }
        );
    }
}
