package com.fullstack.orders.event;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderDeliveredEvent {

    private UUID orderId;
    private UUID shipmentId;
    private String status;
}