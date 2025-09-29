package com.example.likelion13th_spring.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingAddressDto {

    private String recipient;
    private String phoneNumber;
    private String streetAddress;
    private String detailAddress;
    private String postalCode;
}