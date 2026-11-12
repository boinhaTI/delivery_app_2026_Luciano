package com.edu.catolica.seller_ms.dto;

import java.util.List;

public record SellerProductsDTO(
        String name,
        List<ProductDTO> products
) {
}
