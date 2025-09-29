package com.example.likelion13th_spring.repository;

import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByIsDeletedFalse();

    Optional<Orders> findByIdAndIsDeletedFalse(Long id);

    List<Orders> findByBuyerAndIsDeletedFalse(Member buyer);
}