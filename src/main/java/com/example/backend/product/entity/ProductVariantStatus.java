package com.example.backend.product.entity;

public enum ProductVariantStatus {

    ACTIVE, // 可販售
    OUT_OF_STOCK, // 無庫存（可顯示，但不可加入購物車）
    DISABLED; // 停用（後台用，前台不可選）

    public boolean isPurchasable() {
        return this == ACTIVE;
    }
}
