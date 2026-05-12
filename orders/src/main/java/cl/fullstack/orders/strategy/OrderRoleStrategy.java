package cl.fullstack.orders.strategy;

import cl.fullstack.orders.model.Order;
import cl.fullstack.orders.model.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderRoleStrategy {

    boolean supports(String role);

    List<Order> listOrders(UUID userId);

    Order getOrderById(UUID userId, UUID orderId);

    Order updateOrderStatus(UUID userId, UUID orderId, OrderStatus status);
}