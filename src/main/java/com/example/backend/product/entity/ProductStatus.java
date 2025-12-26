package com.example.backend.product.entity;

public enum ProductStatus {

    DRAFT, // 草稿（後台建立中，前台不可見）
    ACTIVE, // 上架中（前台可見）
    INACTIVE, // 下架（前台不可見，但資料保留）
    ARCHIVED; // 封存（停售，不可再販售）

    public boolean isVisible() {
        return this == ACTIVE;
    }

    public boolean isEditable() {
        return this == DRAFT || this == INACTIVE;
    }
}
