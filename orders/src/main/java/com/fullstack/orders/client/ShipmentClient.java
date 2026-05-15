package com.fullstack.orders.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class ShipmentClient {

    private final RestTemplate restTemplate;

    @Value("${shipments.base-url}")
    private String shipmentsBaseUrl;

    public ShipmentClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ShipmentResponse createShipment(UUID orderId, UUID userId) {
        String url = shipmentsBaseUrl;

        ShipmentRequest request = new ShipmentRequest(orderId, userId);

        return restTemplate.postForObject(url, request, ShipmentResponse.class);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShipmentRequest {
        private UUID orderId;
        private UUID userId;
    }

    @Getter
    @Setter
    public static class ShipmentResponse {
        private UUID id;
        private UUID orderId;
        private UUID userId;
        private String status;
    }
}
