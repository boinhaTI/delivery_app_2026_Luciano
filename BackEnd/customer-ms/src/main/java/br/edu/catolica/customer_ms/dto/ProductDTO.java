package br.edu.catolica.customer_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Schema(name = "ProductDTO", description = "Representação dos dados do Product")
public record ProductDTO(

        @Schema(description = "Id do Product", example = "1")
        Long id,

        @Schema(description = "Descrição do Product", example = "Notebook Dell")
        @NotBlank(message = "O campo description é obrigatório")
        String description,

        @Schema(description = "Price do Product", example = "3500.00")
        @NotBlank(message = "O campo price é obrigatório")
        BigDecimal price,

        @Schema(description = "Id do seller", example = "1")
        @NotBlank(message = "O campo sellerId é obrigatório")
        Long sellerId
) {
}
