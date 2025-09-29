package com.example.likelion13th_spring.dto.request;

import com.example.likelion13th_spring.domain.Embedded.ShippingAddress;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrdersRequestDto {

    // 구매자 ID
    private Long buyerId;

    // 배송 정보
    private ShippingAddressDto shippingAddress;

    // 주문할 상품 목록
    private List<ProductOrderRequestDto> productOrders;
}