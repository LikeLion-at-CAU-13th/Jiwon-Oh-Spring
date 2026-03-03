package com.example.likelion13th_spring.repository;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySeller(Member member);
}