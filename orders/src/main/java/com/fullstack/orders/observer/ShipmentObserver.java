package com.fullstack.orders.observer;

import com.fullstack.orders.event.OrderDeliveredEvent;
import com.fullstack.orders.model.Order;
import com.fullstack.orders.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders/events")
@CrossOrigin(origins = "*")
public class ShipmentObserver {

    private final OrderService orderService;

    public ShipmentObserver(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order-delivered")
    public ResponseEntity<?> handleOrderDelivered(@RequestBody OrderDeliveredEvent event) {
        Order updatedOrder = orderService.markOrderAsDelivered(event.getOrderId());
        return ResponseEntity.ok(updatedOrder);
    }
}