package com.example.backend.cart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;
    private Long cartId; // 所屬購物車 ID（後續可改 ManyToOne）
    private Long productId; // 商品 ID
    private Integer quantity; // 數量
    private Integer unitPrice; // 商品單價（加入購物車當下的價格）
    private Integer totalPrice; // 小計（unitPrice * quantity）

    public CartItem(Long cartId, Long productId, Integer quantity, Integer unitPrice, Integer totalPrice) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
}
