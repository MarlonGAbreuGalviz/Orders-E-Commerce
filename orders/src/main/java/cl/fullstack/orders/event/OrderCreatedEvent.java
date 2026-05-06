package cl.fullstack.orders.event;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderCreatedEvent {

    private UUID orderId;
    private UUID userId;
    private UUID productId;
    private Integer quantity;
    private BigDecimal total;
    private String status;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(
            UUID orderId,
            UUID userId,
            UUID productId,
            Integer quantity,
            BigDecimal total,
            String status
    ) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
        this.status = status;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}