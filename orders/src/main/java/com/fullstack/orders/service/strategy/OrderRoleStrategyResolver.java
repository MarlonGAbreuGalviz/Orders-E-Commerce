package com.fullstack.orders.service.strategy;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderRoleStrategyResolver {

    private final List<OrderRoleStrategy> strategies;

    public OrderRoleStrategyResolver(List<OrderRoleStrategy> strategies) {
        this.strategies = strategies;
    }

    public OrderRoleStrategy resolve(String role) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rol no soportado: " + role));
    }
}