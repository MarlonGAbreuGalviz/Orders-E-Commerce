package com.fullstack.orders.service.impl;

import org.springframework.stereotype.Service;

import com.fullstack.orders.dto.CreateOrderRequest;
import com.fullstack.orders.event.PaymentApprovedEvent;
import com.fullstack.orders.model.Order;
import com.fullstack.orders.model.OrderStatus;
import com.fullstack.orders.publisher.OrderEventPublisher;
import com.fullstack.orders.repository.OrderRepository;
import com.fullstack.orders.service.OrderService;
import com.fullstack.orders.strategy.OrderRoleStrategy;
import com.fullstack.orders.strategy.OrderRoleStrategyResolver;

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
    public Order createOrder(CreateOrderRequest request) {
        Order order = Order.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .total(request.getTotal())
                .status(OrderStatus.CREATED)
                .build();

        return saveAndPublish(order);
    }

    @Override
    public Order createOrderFromPayment(PaymentApprovedEvent event) {
        Order order = Order.builder()
                .userId(event.getUserId())
                .productId(event.getProductId())
                .quantity(event.getQuantity())
                .total(event.getTotal())
                .status(OrderStatus.CREATED)
                .build();

        return saveAndPublish(order);
    }

    private Order saveAndPublish(Order order) {
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