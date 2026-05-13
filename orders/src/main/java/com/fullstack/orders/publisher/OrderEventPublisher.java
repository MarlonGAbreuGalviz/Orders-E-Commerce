package com.fullstack.orders.publisher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fullstack.orders.event.OrderCreatedEvent;
import com.fullstack.orders.model.Order;

@Component
public class OrderEventPublisher {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${shipments.observer.url}")
    private String shipmentsObserverUrl;

    public void publishOrderCreated(Order order) {
        try {
            OrderCreatedEvent event = new OrderCreatedEvent(
                    order.getId(),
                    order.getUserId(),
                    order.getProductId(),
                    order.getQuantity(),
                    order.getTotal(),
                    order.getStatus().name()
            );

            restTemplate.postForObject(
                    shipmentsObserverUrl,
                    event,
                    Void.class
            );

            System.out.println("ORDERS: Evento OrderCreated enviado a Shipments");
            System.out.println("ORDERS: Pedido publicado: " + order.getId());

        } catch (RestClientException e) {
            System.out.println("ORDERS: No se pudo enviar el evento a Shipments");
            System.out.println("ORDERS: Error: " + e.getMessage());
        }
    }
}