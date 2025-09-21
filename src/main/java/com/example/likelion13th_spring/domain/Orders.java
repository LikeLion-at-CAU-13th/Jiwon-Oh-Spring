package com.example.likelion13th_spring.domain;

import com.example.likelion13th_spring.domain.Embedded.ShippingAddress;
import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.enums.DeliverStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 배송 id

    @Enumerated(EnumType.STRING)
    private DeliverStatus deliverStatus; // 배송상태

    @ManyToOne
    @JoinColumn(name ="buyer_id")
    private Member buyer; //구매자

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<ProductOrders> productOrders; // 주문한 상품

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private Coupon coupon; //? 쿠폰

    @Embedded
    private ShippingAddress shippingAddress;

    // soft 삭제를 위한 필드 - isDeleted
    private boolean isDeleted;

    public void softDelete(){
        this.isDeleted = true;
    }
}
