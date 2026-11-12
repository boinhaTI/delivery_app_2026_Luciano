package com.edu.catolica.seller_ms.modal;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;


import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();

    }
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
