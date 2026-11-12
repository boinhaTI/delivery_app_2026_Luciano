package com.edu.catolica.seller_ms.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal price;

    private boolean stock;

    @Column(nullable = false, name = "seller_id")
    private Long sellerId;

    @Override
    public void prePersist() {
        this.stock = true;
        super.prePersist();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(description, product.description) && Objects.equals(sellerId, product.sellerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, sellerId);
    }
}
