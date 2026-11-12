package br.edu.catolica.customer_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
@Schema(description = "Representação dos itens de um pedido")
public record ItemResquestDTO(
        @Schema(description = "Id do produto do pedido")
        @NotNull(message = "O id do produto é obrigatório")
        Long productId,

        @Schema(description = "Quantidade de items do produto")
        @NotNull(message = "A quantidade de item é obrigatório")
        @Positive(message = "Quantidade inváçida")
        Integer quantity
) {
}
