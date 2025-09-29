package com.example.likelion13th_spring.dto.request;

import com.example.likelion13th_spring.domain.Embedded.ShippingAddress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersDeleteRequestDto {
    private ShippingAddress shippingAddress;
}
