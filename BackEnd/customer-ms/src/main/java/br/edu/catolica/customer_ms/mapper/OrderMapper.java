package br.edu.catolica.customer_ms.mapper;

import br.edu.catolica.customer_ms.domain.Order;
import br.edu.catolica.customer_ms.dto.OrderRequestDTO;
import br.edu.catolica.customer_ms.utils.IgnoreBaseEntityProperties;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @BeanMapping(builder = @Builder(disableBuilder = true))
    @IgnoreBaseEntityProperties
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "status", ignore = true)
    Order toEntity(OrderRequestDTO orderRequestDTO);
}
