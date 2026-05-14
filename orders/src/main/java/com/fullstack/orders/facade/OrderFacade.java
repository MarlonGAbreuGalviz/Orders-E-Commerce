package com.fullstack.orders.facade;

import com.fullstack.orders.dto.CreateOrderRequest;
import com.fullstack.orders.dto.OrderResponse;
import com.fullstack.orders.model.OrderStatus;
import com.fullstack.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;

    public OrderResponse crearPedido(CreateOrderRequest request) {
        return OrderResponse.fromEntity(orderService.createOrder(request));
    }

    public List<OrderResponse> listarPedidosPorRol(UUID userId, String role) {
        return orderService.getOrdersByRole(userId, role)
                .stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public OrderResponse buscarPedidoPorRol(UUID userId, String role, UUID orderId) {
        return OrderResponse.fromEntity(
                orderService.getOrderByIdByRole(userId, role, orderId)
        );
    }

    public OrderResponse actualizarEstadoPorRol(UUID userId, String role, UUID orderId, OrderStatus status) {
        return OrderResponse.fromEntity(
                orderService.updateOrderStatusByRole(userId, role, orderId, status)
        );
    }
}