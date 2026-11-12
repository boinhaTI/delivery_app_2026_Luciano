package com.edu.catolica.seller_ms.controller;

import com.edu.catolica.seller_ms.dto.ResponseDTO;
import com.edu.catolica.seller_ms.dto.SellerDTO;
import com.edu.catolica.seller_ms.dto.SellerProductsDTO;
import com.edu.catolica.seller_ms.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.edu.catolica.seller_ms.constants.SellerConstants.*;

@RestController
@RequestMapping("/api/v1/seller")
@Tag(name = "API de Seller", description = "API que contem os recursos para gerenciamento dos vendedores")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @Operation(summary = "Cadastro seller", description = "Recurso responsável por registrar um vendedor no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = CUSTOMER_MESSAGE_201),
            @ApiResponse(responseCode = "400", description = CUSTOMER_MESSAGE_400),
            @ApiResponse(responseCode = "500", description = CUSTOMER_MESSAGE_500)
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody @Valid SellerDTO sellerDTO) {
        sellerService.save(sellerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDTO(CUSTOMER_MESSAGE_201, HttpStatus.CREATED.value()));
    }

    @GetMapping("/products/{sellerId}")
    public ResponseEntity<SellerProductsDTO> productsBySellerId(@PathVariable("sellerId") Long sellerId) {
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.sellerProductsDTO(sellerId));
    }
}
