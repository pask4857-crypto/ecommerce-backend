package com.example.backend.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    private Long userId;

    private String receiverName;

    private String phone;

    private String city;

    private String district;

    private String fullAddress;

    private Boolean isDefault;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Address(
            Long userId,
            String receiverName,
            String phone,
            String city,
            String district,
            String fullAddress,
            Boolean isDefault,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.userId = userId;
        this.receiverName = receiverName;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.fullAddress = fullAddress;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
