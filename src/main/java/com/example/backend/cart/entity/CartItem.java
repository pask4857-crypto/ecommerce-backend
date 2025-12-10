package com.example.backend.cart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @Column(name = "cart_id")
    private Long cartId; // 所屬購物車 ID（後續可改 ManyToOne）

    @Column(name = "product_id")
    private Long productId; // 商品 ID

    @Column(name = "quantity")
    private Integer quantity; // 數量

    @Column(name = "unit_price")
    private Integer unitPrice; // 商品單價（加入購物車當下的價格）

    @Column(name = "total_price")
    private Integer totalPrice; // 小計（unitPrice * quantity）
}
