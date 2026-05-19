package com.fullstack.orders.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class PaymentClient {

    private final RestTemplate restTemplate;

    @Value("${payments.base-url}")
    private String paymentsBaseUrl;

    public PaymentClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PaymentResponse processPayment(UUID orderId, UUID userId, BigDecimal total) {
        String url = paymentsBaseUrl + "/process";

        PaymentRequest request = new PaymentRequest(orderId, userId, total);

        return restTemplate.postForObject(url, request, PaymentResponse.class);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentRequest {
        private UUID orderId;
        private UUID userId;
        private BigDecimal total;
    }

    @Getter
    @Setter
    public static class PaymentResponse {
        private UUID paymentId;
        private UUID orderId;
        private String status;
        private boolean approved;
    }
}