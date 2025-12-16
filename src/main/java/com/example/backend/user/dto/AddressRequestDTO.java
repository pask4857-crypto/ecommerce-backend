package com.example.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressRequestDTO {

    private Long userId;

    private String receiverName;

    private String phone;

    private String city;

    private String district;

    private String fullAddress;

    private Boolean isDefault;
}
