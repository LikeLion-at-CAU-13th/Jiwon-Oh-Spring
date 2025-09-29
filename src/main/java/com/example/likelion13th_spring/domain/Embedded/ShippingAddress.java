package com.example.likelion13th_spring.domain.Embedded;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ShippingAddress {

    private String recipient; // 수령인

    private String phoneNumber; // 전화번호

    private String streetAddress; // 도로명주소

    private String detailAddress; // 상세주소

    private String postalCode; // 우편번호
}