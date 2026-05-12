package cl.fullstack.orders.service.impl;

import cl.fullstack.orders.model.Order;
import cl.fullstack.orders.model.OrderStatus;
import cl.fullstack.orders.publisher.OrderEventPublisher;
import cl.fullstack.orders.repository.OrderRepository;
import cl.fullstack.orders.service.OrderService;
import cl.fullstack.orders.strategy.OrderRoleStrategy;
import cl.fullstack.orders.strategy.OrderRoleStrategyResolver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;
    private final OrderRoleStrategyResolver strategyResolver;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderEventPublisher orderEventPublisher,
            OrderRoleStrategyResolver strategyResolver
    ) {
        this.orderRepository = orderRepository;
        this.orderEventPublisher = orderEventPublisher;
        this.strategyResolver = strategyResolver;
    }

    @Override
    public Order createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);

        orderEventPublisher.publishOrderCreated(savedOrder);

        return savedOrder;
    }

    @Override
    public List<Order> getOrdersByRole(UUID userId, String role) {
        OrderRoleStrategy strategy = strategyResolver.resolve(role);
        return strategy.listOrders(userId);
    }

    @Override
    public Order getOrderByIdByRole(UUID userId, String role, UUID orderId) {
        OrderRoleStrategy strategy = strategyResolver.resolve(role);
        return strategy.getOrderById(userId, orderId);
    }

    @Override
    public Order updateOrderStatusByRole(UUID userId, String role, UUID orderId, OrderStatus status) {
        OrderRoleStrategy strategy = strategyResolver.resolve(role);
        return strategy.updateOrderStatus(userId, orderId, status);
    }
}