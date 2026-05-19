package com.fullstack.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstack.orders.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUserId(UUID userId);
}