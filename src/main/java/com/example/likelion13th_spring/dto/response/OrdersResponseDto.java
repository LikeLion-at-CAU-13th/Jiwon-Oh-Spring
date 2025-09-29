package com.example.likelion13th_spring.dto.response;

import com.example.likelion13th_spring.domain.Embedded.ShippingAddress;
import com.example.likelion13th_spring.enums.DeliverStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponseDto {
    private Long orderId;
    private DeliverStatus deliverStatus;
    private ShippingAddress shippingAddress;
    private List<ProductOrderResponseDto> productOrders;

    private String message;
}