package cl.fullstack.orders.service;

import cl.fullstack.orders.model.Order;
import cl.fullstack.orders.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(Order order);

    List<Order> getOrdersByRole(UUID userId, String role);

    Order getOrderByIdByRole(UUID userId, String role, UUID orderId);

    Order updateOrderStatusByRole(UUID userId, String role, UUID orderId, OrderStatus status);
}