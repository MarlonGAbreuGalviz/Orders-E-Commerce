package cl.fullstack.orders.service;

import cl.fullstack.orders.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(Order order);

    List<Order> getAllOrders();

    Order getOrderById(UUID id);
}
