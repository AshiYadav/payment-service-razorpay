package com.payment_microservice.paymentService.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.payment_microservice.paymentService.service.PaymentService;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class RazorPayWebhookUrl {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/rzp/webhook")
    public ResponseEntity<String> handleRazorpayWebhook(@RequestHeader("X-Razorpay-Signature") String signature, @RequestBody String payload) throws Exception {
        // Process the webhook payload
        System.out.println("Received Razorpay Webhook");
        System.out.println("Signature: " + signature);
        System.out.println("Payload: " + payload);

        // Verify the webhook signature
        boolean isValidSignature = verifySignature(payload, signature);

        if (isValidSignature) {
            // Process the payment details
            processPaymentDetails(payload);
            return ResponseEntity.ok("Webhook processed successfully");
        } else {
            return ResponseEntity.status(400).body("Invalid signature");
        }
    }

    private boolean verifySignature(String payload, String signature) {
        // Implement signature verification logic using Razorpay's SDK or manually
        // This is crucial to ensure the webhook request is genuine
        // Refer to Razorpay's documentation for details on signature verification
        try {
            Utils.verifyWebhookSignature(payload, signature, "123456789");
            return true;
        } catch (RazorpayException e) {
            e.printStackTrace();
            return false;
        }
         // Simplified for demonstration
    }

    private void processPaymentDetails(String payload) throws Exception {
        // Parse the payload and perform necessary actions (e.g., update order status)
        // You can use a library like Jackson to parse the JSON payload

        //JSON.stringify(payload);
        //String jsonString = "{\"entity\":\"event\",\"account_id\":\"acc_OHa92HmvkLrfCv\",\"event\":\"payment.captured\",\"contains\":[\"payment\"],\"payload\":{\"payment\":{\"entity\":{\"id\":\"pay_OJC6Q7VAIr9zkc\",\"entity\":\"payment\",\"amount\":90000,\"currency\":\"INR\",\"status\":\"captured\",\"order_id\":\"order_OJC60JGXp7Pn7p\",\"invoice_id\":null,\"international\":false,\"method\":\"card\",\"amount_refunded\":0,\"refund_status\":null,\"captured\":true,\"description\":\"#OJC5u0rxuryrsN\",\"card_id\":\"card_OJC6QLoJZwD7Ej\",\"card\":{\"id\":\"card_OJC6QLoJZwD7Ej\",\"entity\":\"card\",\"name\":\"\",\"last4\":\"4366\",\"network\":\"Visa\",\"type\":\"credit\",\"issuer\":\"UTIB\",\"international\":false,\"emi\":true,\"sub_type\":\"consumer\",\"token_iin\":null},\"bank\":null,\"wallet\":null,\"vpa\":null,\"email\":\"void@razorpay.com\",\"contact\":\"+918860389696\",\"notes\":{\"MyOrderId\":\"1db7d89b-4f45-447b-8478-671fba6c0f7c\",\"MyPaymentId\":\"fad30eb4-c3b9-47f3-b26e-883ea9724ab2\"},\"fee\":1800,\"tax\":0,\"error_code\":null,\"error_description\":null,\"error_source\":null,\"error_step\":null,\"error_reason\":null,\"acquirer_data\":{\"auth_code\":\"546366\"},\"emi_plan\":null,\"created_at\":1717615563,\"reward\":null,\"base_amount\":90000}}},\"created_at\":1717615570}";

        JsonObject jsonObject = JsonParser.parseString(payload).getAsJsonObject();
        String eventType = jsonObject.get("event").getAsString();

        if(eventType.equalsIgnoreCase("payment.captured")) {
            JsonObject notes = jsonObject
                    .getAsJsonObject("payload")
                    .getAsJsonObject("payment")
                    .getAsJsonObject("entity")
                    .getAsJsonObject("notes");

            Map<String, String> notesMap = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : notes.entrySet()) {
                notesMap.put(entry.getKey(), entry.getValue().getAsString());
            }

            UUID id = UUID.fromString(notesMap.get("MyPaymentId"));
            System.out.println("My Paymennt UUID is" + id);

            paymentService.updatePaymentStatus(id);
        }


    }
}
