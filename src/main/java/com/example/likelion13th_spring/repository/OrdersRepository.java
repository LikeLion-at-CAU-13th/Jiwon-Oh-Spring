package com.example.likelion13th_spring.repository;

import com.example.likelion13th_spring.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
