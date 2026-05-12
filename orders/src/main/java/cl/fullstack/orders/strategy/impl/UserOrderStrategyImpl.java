package cl.fullstack.orders.strategy.impl;

import cl.fullstack.orders.model.Order;
import cl.fullstack.orders.model.OrderStatus;
import cl.fullstack.orders.repository.OrderRepository;
import cl.fullstack.orders.strategy.OrderRoleStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Component
public class UserOrderStrategyImpl implements OrderRoleStrategy {

    private final OrderRepository orderRepository;

    public UserOrderStrategyImpl(OrderRepository orderRepository) {
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