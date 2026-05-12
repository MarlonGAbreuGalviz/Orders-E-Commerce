package cl.fullstack.orders.controller;

import cl.fullstack.orders.model.Order;
import cl.fullstack.orders.model.OrderStatus;
import cl.fullstack.orders.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping
    public ResponseEntity<?> getOrdersByRole(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role
    ) {
        return ResponseEntity.ok(orderService.getOrdersByRole(userId, role));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderByIdByRole(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role,
            @PathVariable UUID orderId
    ) {
        return ResponseEntity.ok(orderService.getOrderByIdByRole(userId, role, orderId));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatusByRole(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-Role") String role,
            @PathVariable UUID orderId,
            @RequestParam OrderStatus status
    ) {
        return ResponseEntity.ok(
                orderService.updateOrderStatusByRole(userId, role, orderId, status)
        );
    }
}