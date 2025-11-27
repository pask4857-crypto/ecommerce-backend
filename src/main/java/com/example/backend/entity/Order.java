package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    // 下單者姓名
    private String Name;

    // 下單者電話
    private String Phone;

    // 收件地址
    private String Address;

    // 訂單總金額
    private Integer totalAmount;

    // 訂單建立時間
    private LocalDateTime createdAt;

    // 一張訂單有多個項目
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> Items;
}
