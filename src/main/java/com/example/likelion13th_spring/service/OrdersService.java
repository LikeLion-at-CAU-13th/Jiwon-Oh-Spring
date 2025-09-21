package com.example.likelion13th_spring.service;

import com.example.likelion13th_spring.domain.Embedded.ShippingAddress;
import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.domain.Product;
import com.example.likelion13th_spring.dto.request.OrdersRequestDto;
import com.example.likelion13th_spring.dto.request.ProductOrderRequestDto;
import com.example.likelion13th_spring.dto.response.OrdersResponseDto;
import com.example.likelion13th_spring.dto.response.ProductOrderResponseDto;
import com.example.likelion13th_spring.enums.DeliverStatus;
import com.example.likelion13th_spring.repository.MemberRepository;
import com.example.likelion13th_spring.repository.OrdersRepository;
import com.example.likelion13th_spring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Orders createOrder(OrdersRequestDto requestDto) {
        Member buyer = memberRepository.findById(requestDto.getBuyerId())
                .orElseThrow(() -> new IllegalArgumentException("구매자를 찾을 수 없습니다."));

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setRecipient(requestDto.getShippingAddress().getRecipient());
        shippingAddress.setPhoneNumber(requestDto.getShippingAddress().getPhoneNumber());
        shippingAddress.setStreetAddress(requestDto.getShippingAddress().getStreetAddress());
        shippingAddress.setDetailAddress(requestDto.getShippingAddress().getDetailAddress());
        shippingAddress.setPostalCode(requestDto.getShippingAddress().getPostalCode());

        Orders orders = Orders.builder()
                .buyer(buyer)
                .deliverStatus(DeliverStatus.PREPARATION) // 주문 생성 시 초기 상태
                .shippingAddress(shippingAddress)
                .build();

        List<ProductOrders> productOrdersList = requestDto.getProductOrders().stream()
                .map(productOrderDto -> {
                    Product product = productRepository.findById(productOrderDto.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

                    return ProductOrders.builder()
                            .product(product)
                            .orders(orders)
                            .quantity(productOrderDto.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());

        orders.setProductOrders(productOrdersList);

        return ordersRepository.save(orders);
    }

    // 주문 전체 조회
    @Transactional
    public List<OrdersResponseDto> getAllOrders() {
        List<Orders> ordersList = ordersRepository.findAll();

        return ordersList.stream()
                .map(this::mapToOrdersResponseDto)
                .collect(Collectors.toList());
    }

    // 상품별 주문
    @Transactional
    public OrdersResponseDto getOrderById(Long orderId) {
        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문 ID를 찾을 수 없습니다: " + orderId));
        return mapToOrdersResponseDto(orders);
    }

    @Transactional
    public List<OrdersResponseDto> getOrdersByBuyerId(Long buyerId) {
        Member buyer = memberRepository.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("구매자를 찾을 수 없습니다: " + buyerId));
        List<Orders> ordersList = ordersRepository.findByBuyer(buyer);
        return ordersList.stream()
                .map(this::mapToOrdersResponseDto)
                .collect(Collectors.toList());
    }

    private OrdersResponseDto mapToOrdersResponseDto(Orders orders) {
        List<ProductOrderResponseDto> productOrdersDtoList = orders.getProductOrders().stream()
                .map(productOrders -> ProductOrderResponseDto.builder()
                        .productId(productOrders.getProduct().getId())
                        .productName(productOrders.getProduct().getName())
                        .price(productOrders.getProduct().getPrice())
                        .quantity(productOrders.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return OrdersResponseDto.builder()
                .orderId(orders.getId())
                .deliverStatus(orders.getDeliverStatus())
                .shippingAddress(orders.getShippingAddress())
                .productOrders(productOrdersDtoList)
                .build();
    }
}