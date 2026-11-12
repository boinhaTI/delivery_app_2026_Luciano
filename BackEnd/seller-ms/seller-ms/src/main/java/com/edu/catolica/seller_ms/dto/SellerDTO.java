package com.edu.catolica.seller_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(name = "SellerDTO", description = "Representação dos dados do Seller")
public record SellerDTO(

        @Schema(description = "Nome do Seller", minLength = 2, example = "Maria do Carmo")
        @NotBlank(message = "O campo name é obrigatório")
        @Size(min = 2, message = "O nome não deve ser menor que 2 caracteres")
        String name,

        @Schema(description = "PhoneNumber do Seller", example = "(88) 99999-9999")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$",
                message = "Telefone inválido"
        )
        @NotBlank(message = "O campo phoneNumber é obrigatório")
        String phoneNumber
) {
}
