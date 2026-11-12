package br.edu.catolica.customer_ms.mapper;

import br.edu.catolica.customer_ms.domain.Customer;
import br.edu.catolica.customer_ms.dto.CustomerDTO;
import br.edu.catolica.customer_ms.utils.IgnoreBaseEntityProperties;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mapping(target = "address.id", ignore = true)
    @Mapping(target = "address", source = "address")
    @IgnoreBaseEntityProperties
    Customer dtoToEntity(CustomerDTO customerDTO);

}