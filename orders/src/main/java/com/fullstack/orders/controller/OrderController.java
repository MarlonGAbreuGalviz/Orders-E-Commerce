package com.fullstack.orders.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fullstack.orders.dto.CreateOrderRequest;
import com.fullstack.orders.dto.OrderResponse;
import com.fullstack.orders.facade.OrderFacade;
import com.fullstack.orders.model.OrderStatus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderResponse response = orderFacade.crearPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrdersByRole(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role
    ) {
        return ResponseEntity.ok(orderFacade.listarPedidosPorRol(userId, role));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderByIdByRole(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role,
            @PathVariable UUID orderId
    ) {
        return ResponseEntity.ok(orderFacade.buscarPedidoPorRol(userId, role, orderId));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatusByRole(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role,
            @PathVariable UUID orderId,
            @RequestParam OrderStatus status
    ) {
        return ResponseEntity.ok(orderFacade.actualizarEstadoPorRol(userId, role, orderId, status));
    }
}