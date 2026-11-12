package com.edu.catolica.seller_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Schema(name = "ProductDTO", description = "Representação dos dados do Produto")
public record ProductDTO(

        @Schema(description = "Id do Product", example = "1")
        Long id,

        @Schema(description = "Descrição do Product", example = "Notebook Dell")
        @NotBlank(message = "O campo description é obrigatório")
        String description,

        @Schema(description = "Price do Product", example = "3500.00")
        @NotNull(message = "O campo price é obrigatório")
        @Positive(message = "O preço do produto está inválido")
        BigDecimal price,

        @Schema(description = "Id do seller", example = "1")
        @NotNull
        @Positive
        Long sellerId
) {
}
