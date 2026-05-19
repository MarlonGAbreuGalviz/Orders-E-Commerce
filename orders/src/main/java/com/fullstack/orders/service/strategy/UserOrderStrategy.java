package com.fullstack.orders.service.strategy;

import com.fullstack.orders.model.Order;
import com.fullstack.orders.model.OrderStatus;
import com.fullstack.orders.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Component
public class UserOrderStrategy implements OrderRoleStrategy {

    private final OrderRepository orderRepository;

    public UserOrderStrategy(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean supports(String role) {
        return "USER".equalsIgnoreCase(role);
    }

    @Override
    public List<Order> listOrders(UUID userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Order getOrderById(UUID userId, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado con ID: " + orderId
                ));

        if (!order.getUserId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para ver este pedido"
            );
        }

        return order;
    }

    @Override
    public Order updateOrderStatus(UUID userId, UUID orderId, OrderStatus status) {
        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "El usuario no puede cambiar el estado del pedido"
        );
    }
}