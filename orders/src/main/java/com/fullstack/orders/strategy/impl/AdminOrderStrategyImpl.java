package com.fullstack.orders.strategy.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.fullstack.orders.model.Order;
import com.fullstack.orders.model.OrderStatus;
import com.fullstack.orders.repository.OrderRepository;
import com.fullstack.orders.strategy.OrderRoleStrategy;

import java.util.List;
import java.util.UUID;

@Component
public class AdminOrderStrategyImpl implements OrderRoleStrategy {

    private final OrderRepository orderRepository;

    public AdminOrderStrategyImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean supports(String role) {
        return "ADMIN".equalsIgnoreCase(role);
    }

    @Override
    public List<Order> listOrders(UUID userId) {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(UUID userId, UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado con ID: " + orderId
                ));
    }

    @Override
    public Order updateOrderStatus(UUID userId, UUID orderId, OrderStatus status) {
        Order order = getOrderById(userId, orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}