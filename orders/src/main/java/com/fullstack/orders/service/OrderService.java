package com.fullstack.orders.service;

import java.util.List;
import java.util.UUID;

import com.fullstack.orders.dto.CreateOrderRequest;
import com.fullstack.orders.event.PaymentApprovedEvent;
import com.fullstack.orders.model.Order;
import com.fullstack.orders.model.OrderStatus;

public interface OrderService {

    Order createOrder(CreateOrderRequest request);

    Order createOrderFromPayment(PaymentApprovedEvent event);

    List<Order> getOrdersByRole(UUID userId, String role);

    Order getOrderByIdByRole(UUID userId, String role, UUID orderId);

    Order updateOrderStatusByRole(UUID userId, String role, UUID orderId, OrderStatus status);
}