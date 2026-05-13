package com.fullstack.orders.strategy;

import java.util.List;
import java.util.UUID;

import com.fullstack.orders.model.Order;
import com.fullstack.orders.model.OrderStatus;

public interface OrderRoleStrategy {

    boolean supports(String role);

    List<Order> listOrders(UUID userId);

    Order getOrderById(UUID userId, UUID orderId);

    Order updateOrderStatus(UUID userId, UUID orderId, OrderStatus status);
}