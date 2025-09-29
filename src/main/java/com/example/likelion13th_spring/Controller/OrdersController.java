package com.example.likelion13th_spring.Controller;

import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.dto.request.OrdersDeleteRequestDto;
import com.example.likelion13th_spring.dto.request.OrdersRequestDto;
import com.example.likelion13th_spring.dto.request.OrdersUpdateRequestDto;
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

        Orders createdOrder = ordersService.createOrder(requestDto);

        OrdersResponseDto responseDto = OrdersResponseDto.builder()
                .orderId(createdOrder.getId())
                .message("주문이 성공적으로 생성되었습니다.")
                .deliverStatus(createdOrder.getDeliverStatus())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping// 전체 주문 조회
    public ResponseEntity<List<OrdersResponseDto>> getAllOrders() {
        List<OrdersResponseDto> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{orderId}") // 상품별 주문 조회
    public ResponseEntity<OrdersResponseDto> getOrderById(@PathVariable Long orderId) {
        OrdersResponseDto orders = ordersService.getOrderById(orderId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/buyers/{buyerId}") // 구매자별 주문 조회
    public ResponseEntity<List<OrdersResponseDto>> getOrdersByBuyerId(@PathVariable Long buyerId) {
        List<OrdersResponseDto> orders = ordersService.getOrdersByBuyerId(buyerId);
        return ResponseEntity.ok(orders);
    }
    @PatchMapping("/{orderId}")
    public ResponseEntity<OrdersResponseDto> updateOrder(@PathVariable Long orderId, @RequestBody OrdersUpdateRequestDto requestDto) {
        Orders updatedOrder = ordersService.updateOrder(orderId, requestDto);
        // 갱신
        OrdersResponseDto responseDto = OrdersResponseDto.builder()
                .orderId(updatedOrder.getId())
                .deliverStatus(updatedOrder.getDeliverStatus())
                .shippingAddress(updatedOrder.getShippingAddress())
                .productOrders(null)
                .build();
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrdersResponseDto> deleteOrder(@PathVariable Long orderId) {
        ordersService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}