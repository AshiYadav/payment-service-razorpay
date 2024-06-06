package com.payment_microservice.paymentService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @CreationTimestamp
    public Instant createdAt;
    @UpdateTimestamp
    public Instant updatedAt;

    private boolean isActive;

    public String updatedBy;
    public String createdBy;

}
