package com.fullstack.orders.dto;

import com.fullstack.orders.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusDTO {

    @NotNull(message = "El estado es obligatorio")
    private OrderStatus status;
}