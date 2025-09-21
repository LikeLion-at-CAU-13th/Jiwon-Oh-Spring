package com.example.likelion13th_spring.Controller;

import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.dto.request.OrdersRequestDto;
import com.example.likelion13th_spring.dto.response.OrdersResponseDto;
import com.example.likelion13th_spring.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<OrdersResponseDto> createOrder(@RequestBody OrdersRequestDto requestDto) {

        // 1. 서비스 계층을 호출하여 주문을 생성하고, Orders 엔티티를 반환받습니다.
        Orders createdOrder = ordersService.createOrder(requestDto);

        // 2. Orders 엔티티를 OrdersResponseDto로 변환합니다.
        OrdersResponseDto responseDto = OrdersResponseDto.builder()
                .orderId(createdOrder.getId())
                .message("주문이 성공적으로 생성되었습니다.")
                .deliverStatus(createdOrder.getDeliverStatus())
                .build();

        // 3. HttpStatus.CREATED(201)와 함께 응답 DTO를 반환합니다.
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }


}