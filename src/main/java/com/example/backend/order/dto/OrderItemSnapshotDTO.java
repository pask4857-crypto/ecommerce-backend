package com.example.backend.order.dto;

import java.math.BigDecimal;

import com.example.backend.order.entity.OrderItem;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemSnapshotDTO {

    private Long productId;
    private String productName;
    private Integer quantity;

    @Column(precision = 19, scale = 2)
    private BigDecimal price; // 下單當下單價

    @Column(precision = 19, scale = 2)
    private BigDecimal subtotal; // price * quantity

    // Optional: 可以加一個靜態工廠方法方便從 OrderItem 生成快照
    public static OrderItemSnapshotDTO fromOrderItem(OrderItem item) {
        return new OrderItemSnapshotDTO(
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getPrice(),
                item.getSubtotal());
    }
}
