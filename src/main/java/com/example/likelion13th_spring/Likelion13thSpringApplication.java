package com.example.likelion13th_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Likelion13thSpringApplication {

	public static void main(String[] args) {

		SpringApplication.run(Likelion13thSpringApplication.class, args);
	}

}
