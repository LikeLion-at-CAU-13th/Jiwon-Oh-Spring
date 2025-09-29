package com.example.likelion13th_spring.repository;

import com.example.likelion13th_spring.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}