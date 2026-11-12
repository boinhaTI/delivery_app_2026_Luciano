package com.edu.catolica.seller_ms.mapper;

import com.edu.catolica.seller_ms.dto.SellerDTO;
import com.edu.catolica.seller_ms.modal.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface SellerMapper {

    @Mapping(target = "id", ignore = true)
    Seller toEntity(SellerDTO sellerDTO);
    SellerDTO toDto(Seller seller);
}
