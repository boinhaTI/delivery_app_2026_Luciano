package com.edu.catolica.seller_ms.mapper;

import com.edu.catolica.seller_ms.dto.ProductDTO;
import com.edu.catolica.seller_ms.modal.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stock", ignore = true)
    Product dtoToProduct(ProductDTO productDTO);

    ProductDTO productToDto(Product product);

    List<ProductDTO> entityToDTOList(List<Product> products);
}
