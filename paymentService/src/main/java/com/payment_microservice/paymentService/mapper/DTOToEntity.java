package com.payment_microservice.paymentService.mapper;

import com.payment_microservice.paymentService.dto.PaymentRequestDTO;

import com.payment_microservice.paymentService.entity.Payment;
import com.payment_microservice.paymentService.entity.PaymentStatus;
import com.razorpay.Product;

import java.util.UUID;

public class DTOToEntity {

    public static Payment convertDTOTOEntity(PaymentRequestDTO dto){
        Payment pay = new Payment();
        pay.setAmount(dto.getAmount());
//        pay.setCurrency(dto.getCurrency());
        pay.setOrderId(dto.getOrderId());
        pay.setPaymentStatus(PaymentStatus.PENDING);
//        pay.s(UUID.randomUUID());
        //pay.setTransactionId();

        return pay;
    }

//    public PaymentRequestDTO convertEntityTODTO(Payment payment){
//        Payment pay = new Payment();
//        pay.setAmount(dto.getAmount());
//        pay.setCurrency(dto.getCurrency());
//        pay.setOrderId(dto.getOrderId());
//        pay.setPaymentStatus(PaymentStatus.PENDING);
//        pay.setUserUUID(UUID.randomUUID());
//        //pay.setTransactionId();
//
//        return pay;
//    }
}
