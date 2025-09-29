package com.example.likelion13th_spring.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOrderRequestDto {

    private Long productId;
    private Integer quantity;
}