package com.fullstack.orders.service;

import com.fullstack.orders.dto.OrderRequestDTO;
import com.fullstack.orders.model.Order;
import com.fullstack.orders.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createPendingOrder(OrderRequestDTO request);

    Order payOrder(UUID orderId);

    List<Order> getOrdersByRole(UUID userId, String role);

    Order getOrderByIdByRole(UUID userId, String role, UUID orderId);

    Order updateOrderStatusByRole(UUID userId, String role, UUID orderId, OrderStatus status);

    Order markOrderAsDelivered(UUID orderId);
}