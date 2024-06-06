package com.payment_microservice.paymentService.configuration;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RazorpayClientConfig {

    @Value("${razorpay.client.key}")
    private String key;

    @Value("${razorpay.client.secret}")
    private String secret;

    @Bean
    public RazorpayClient getRazorpayClient() throws RazorpayException {
        return new RazorpayClient(key, secret);
    }
}

