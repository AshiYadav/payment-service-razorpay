package com.payment_microservice.paymentService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Payment extends BaseModel{

    public UUID orderId;
    public UUID userUUID;
    public String transactionId;
    public double amount;
    @Enumerated(EnumType.STRING)
    public PaymentStatus paymentStatus;
    @OneToOne
    public Currency currency;



}
