package com.payment_microservice.paymentService.controller;

import com.payment_microservice.paymentService.dto.PaymentRequestDTO;
import com.payment_microservice.paymentService.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PapymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/start")
    public ResponseEntity doPayment(@RequestBody PaymentRequestDTO dto) throws RazorpayException {
        return ResponseEntity.ok(paymentService.generatePaymentLink(dto));
    }

}
