package com.example.likelion13th_spring.dto.request;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.domain.Product;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String name;
    private Integer price;
    private Integer stock;
    private String description;
    private Long memberId;

    public Product toEntity(Member seller) {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .stock(this.stock)
                .description(this.description)
                .seller(seller)
                .build();
    }
}


