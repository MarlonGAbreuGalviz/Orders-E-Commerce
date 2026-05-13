package com.fullstack.orders.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fullstack.orders.dto.CreateOrderRequest;
import com.fullstack.orders.dto.OrderResponse;
import com.fullstack.orders.model.Order;
import com.fullstack.orders.model.OrderStatus;
import com.fullstack.orders.service.OrderService;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order createdOrder = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.fromEntity(createdOrder));
    }

    @GetMapping
    public ResponseEntity<?> getOrdersByRole(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role
    ) {
        return ResponseEntity.ok(
                orderService.getOrdersByRole(userId, role)
                        .stream()
                        .map(OrderResponse::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderByIdByRole(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role,
            @PathVariable UUID orderId
    ) {
        Order order = orderService.getOrderByIdByRole(userId, role, orderId);
        return ResponseEntity.ok(OrderResponse.fromEntity(order));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatusByRole(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role,
            @PathVariable UUID orderId,
            @RequestParam OrderStatus status
    ) {
        Order updatedOrder = orderService.updateOrderStatusByRole(userId, role, orderId, status);
        return ResponseEntity.ok(OrderResponse.fromEntity(updatedOrder));
    }
}