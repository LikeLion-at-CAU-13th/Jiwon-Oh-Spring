package com.example.likelion13th_spring.dto.response;

import com.example.likelion13th_spring.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

@Getter
@Builder //추가
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private Integer stock;
    private String description;

    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .description(product.getDescription())
                .build();
    }
}

