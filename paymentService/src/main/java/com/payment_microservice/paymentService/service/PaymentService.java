package com.payment_microservice.paymentService.service;

import com.payment_microservice.paymentService.dto.PaymentRequestDTO;
import com.payment_microservice.paymentService.entity.Payment;
import com.razorpay.RazorpayException;

import java.util.UUID;

public interface PaymentService {

    public String generatePaymentLink(PaymentRequestDTO dto) throws RazorpayException;
    public Payment updatePaymentStatus(UUID id) throws Exception;
}
