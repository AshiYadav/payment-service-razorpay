package com.payment_microservice.paymentService.dto;

import com.payment_microservice.paymentService.entity.Currency;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class PaymentRequestDTO {

    private double amount;
    private UUID orderId;
    private String description;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
}
