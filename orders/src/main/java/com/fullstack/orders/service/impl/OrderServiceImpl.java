package com.fullstack.orders.service.impl;

import com.fullstack.orders.client.InventoryClient;
import com.fullstack.orders.client.PaymentClient;
import com.fullstack.orders.client.ShipmentClient;
import com.fullstack.orders.dto.OrderRequestDTO;
import com.fullstack.orders.model.Order;
import com.fullstack.orders.model.OrderStatus;
import com.fullstack.orders.repository.OrderRepository;
import com.fullstack.orders.service.OrderService;
import com.fullstack.orders.service.strategy.OrderRoleStrategy;
import com.fullstack.orders.service.strategy.OrderRoleStrategyResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final PaymentClient paymentClient;
    private final ShipmentClient shipmentClient;
    private final OrderRoleStrategyResolver strategyResolver;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            InventoryClient inventoryClient,
            PaymentClient paymentClient,
            ShipmentClient shipmentClient,
            OrderRoleStrategyResolver strategyResolver
    ) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.paymentClient = paymentClient;
        this.shipmentClient = shipmentClient;
        this.strategyResolver = strategyResolver;
    }

    @Override
    public Order createPendingOrder(OrderRequestDTO request) {
        boolean hasStock = inventoryClient.hasStock(
                request.getProductId(),
                request.getQuantity()
        );

        if (!hasStock) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No hay stock suficiente para crear el pedido"
            );
        }

        Order order = Order.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .total(request.getTotal())
                .status(OrderStatus.PENDING_PAYMENT)
                .build();

        return orderRepository.save(order);
    }

    @Override
    public Order payOrder(UUID orderId) {
        Order order = getOrderById(orderId);

        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Solo se pueden pagar pedidos en estado PENDING_PAYMENT"
            );
        }

        PaymentClient.PaymentResponse paymentResponse = paymentClient.processPayment(
                order.getId(),
                order.getUserId(),
                order.getTotal()
        );

        if (paymentResponse == null || !paymentResponse.isApproved()) {
            order.setStatus(OrderStatus.CANCELLED);
            return orderRepository.save(order);
        }

        inventoryClient.discountStock(order.getProductId(), order.getQuantity());

        order.setStatus(OrderStatus.PAID);
        Order paidOrder = orderRepository.save(order);

        shipmentClient.createShipment(
                paidOrder.getId(),
                paidOrder.getUserId()
        );

        return paidOrder;
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

    @Override
    public Order markOrderAsDelivered(UUID orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }

    private Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido no encontrado con ID: " + orderId
                ));
    }
}