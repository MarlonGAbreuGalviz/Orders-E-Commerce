package com.fullstack.orders.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApprovedEvent {

    private UUID paymentId;
    private UUID userId;
    private UUID productId;
    private Integer quantity;
    private BigDecimal total;
    private String status;
}