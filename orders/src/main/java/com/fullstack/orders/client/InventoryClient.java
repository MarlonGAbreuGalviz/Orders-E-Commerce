package com.fullstack.orders.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class InventoryClient {

    private final RestTemplate restTemplate;

    @Value("${inventory.base-url}")
    private String inventoryBaseUrl;

    public InventoryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean hasStock(UUID productId, Integer quantity) {
        String url = inventoryBaseUrl + "/products/" + productId + "/check-stock?quantity=" + quantity;

        StockCheckResponse response = restTemplate.getForObject(url, StockCheckResponse.class);

        return response != null && response.isAvailable();
    }

    public void discountStock(UUID productId, Integer quantity) {
        String url = inventoryBaseUrl + "/products/" + productId + "/discount?quantity=" + quantity;

        restTemplate.patchForObject(url, null, Void.class);
    }

    @Getter
    @Setter
    public static class StockCheckResponse {
        private boolean available;
        private Integer currentStock;
        private Integer requestedQuantity;
    }
}