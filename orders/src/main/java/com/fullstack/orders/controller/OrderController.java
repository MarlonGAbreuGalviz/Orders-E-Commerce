package com.fullstack.orders.controller;

import com.fullstack.orders.dto.OrderRequestDTO;
import com.fullstack.orders.dto.OrderResponseDTO;
import com.fullstack.orders.dto.UpdateStatusDTO;
import com.fullstack.orders.model.Order;
import com.fullstack.orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createPendingOrder(
            @Valid @RequestBody OrderRequestDTO request
    ) {
        Order order = orderService.createPendingOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponseDTO.fromEntity(order));
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<OrderResponseDTO> payOrder(@PathVariable UUID orderId) {
        Order order = orderService.payOrder(orderId);
        return ResponseEntity.ok(OrderResponseDTO.fromEntity(order));
    }

    @GetMapping
    public ResponseEntity<?> getOrdersByRole(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role
    ) {
        return ResponseEntity.ok(
                orderService.getOrdersByRole(userId, role)
                        .stream()
                        .map(OrderResponseDTO::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderByIdByRole(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role,
            @PathVariable UUID orderId
    ) {
        Order order = orderService.getOrderByIdByRole(userId, role, orderId);
        return ResponseEntity.ok(OrderResponseDTO.fromEntity(order));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatusByRole(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role,
            @PathVariable UUID orderId,
            @Valid @RequestBody UpdateStatusDTO request
    ) {
        Order order = orderService.updateOrderStatusByRole(
                userId,
                role,
                orderId,
                request.getStatus()
        );

        return ResponseEntity.ok(OrderResponseDTO.fromEntity(order));
    }
}