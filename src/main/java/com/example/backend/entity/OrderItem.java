package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    // ⭐ 關聯到訂單
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // ⭐ 關聯到商品（你已有 Product Entity）
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity; // 數量
    private Integer price; // 商品單價（下單當下）
    private Integer subtotal; // 小計 = price × quantity
}
