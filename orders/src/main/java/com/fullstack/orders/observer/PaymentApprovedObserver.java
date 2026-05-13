package com.fullstack.orders.observer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fullstack.orders.event.PaymentApprovedEvent;
import com.fullstack.orders.service.OrderService;

@RestController
@RequestMapping("/api/orders/observer")
@CrossOrigin(origins = "*")
public class PaymentApprovedObserver {

    private final OrderService orderService;

    public PaymentApprovedObserver(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/payment-approved")
    public ResponseEntity<Void> handlePaymentApproved(@RequestBody PaymentApprovedEvent event) {
        System.out.println("ORDERS OBSERVER: Evento PaymentApproved recibido desde Payments");
        System.out.println("ORDERS OBSERVER: Creando pedido para el pago " + event.getPaymentId());

        orderService.createOrderFromPayment(event);

        System.out.println("ORDERS OBSERVER: Pedido creado correctamente");

        return ResponseEntity.ok().build();
    }
}