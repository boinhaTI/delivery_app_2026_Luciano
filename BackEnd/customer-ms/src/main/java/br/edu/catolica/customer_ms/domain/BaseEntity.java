package br.edu.catolica.customer_ms.domain;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
        isActive = true;
    }
    @PreUpdate
    public void preUpdate(){
        updatedAt = LocalDateTime.now();
    }

}