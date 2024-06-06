package com.payment_microservice.paymentService.service;

import com.payment_microservice.paymentService.configuration.RazorpayClientConfig;
import com.payment_microservice.paymentService.dto.PaymentRequestDTO;
import com.payment_microservice.paymentService.entity.Payment;
import com.payment_microservice.paymentService.entity.PaymentStatus;
import com.payment_microservice.paymentService.mapper.DTOToEntity;
import com.payment_microservice.paymentService.repository.PaymentRepository;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RazorpayClient razorpayClient;




    @Override
    public String generatePaymentLink(PaymentRequestDTO dto) throws RazorpayException {

        //Inserting request dto in payment table
        Payment pay = paymentRepository.save(DTOToEntity.convertDTOTOEntity(dto));

        //Generating payment link

//        RazorpayClient razorPayClient = razorpayClientConfig.getRazorpayClient();
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",dto.getAmount());
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",false);
        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by", Instant.now().toEpochMilli() + 600000);
        paymentLinkRequest.put("reference_id",dto.getOrderId());
        paymentLinkRequest.put("description",dto.getDescription());
        JSONObject customer = new JSONObject();
        customer.put("name",dto.getCustomerName());
        customer.put("contact",dto.getCustomerPhone());
        customer.put("email",dto.getCustomerEmail());
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("notify",notify);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
        notes.put("MyOrderId",dto.getOrderId());
        notes.put("MyPaymentId",pay.getId());
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://fuzzy-worlds-heal.loca.lt/rzp/webhook");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        return payment.toString();
    }

    @Override
    public Payment updatePaymentStatus(UUID id) throws Exception {
        Payment pay = paymentRepository.findById(id).orElseThrow(() -> {
            return new Exception("Payment ID  not found");
        });
        if(pay != null){
            pay.setPaymentStatus(PaymentStatus.SUCCESSFUL);
            return paymentRepository.save(pay);
        }
        return  null;
    }
}
