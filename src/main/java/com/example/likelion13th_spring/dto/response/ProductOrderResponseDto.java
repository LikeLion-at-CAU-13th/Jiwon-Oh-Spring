package com.example.likelion13th_spring.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOrderResponseDto {
    private Long productId;
    private String productName;
    private Integer price;
    private Integer quantity;
}