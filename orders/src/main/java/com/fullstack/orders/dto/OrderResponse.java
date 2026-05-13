package com.fullstack.orders.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fullstack.orders.model.Order;
import com.fullstack.orders.model.OrderStatus;

@Getter
@Builder
public class OrderResponse {

    private UUID id;
    private UUID userId;
    private UUID productId;
    private Integer quantity;
    private BigDecimal total;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public static OrderResponse fromEntity(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .total(order.getTotal())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}