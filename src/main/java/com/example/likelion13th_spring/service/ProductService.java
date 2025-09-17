package com.example.likelion13th_spring.service;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.domain.Product;
import com.example.likelion13th_spring.dto.request.ProductRequestDto;
import com.example.likelion13th_spring.dto.response.ProductResponseDto;
import com.example.likelion13th_spring.repository.MemberRepository;
import com.example.likelion13th_spring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.likelion13th_spring.dto.request.ProductUpdateRequestDto; // 추가
import com.example.likelion13th_spring.dto.request.ProductDeleteRequestDto; // 추가


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional //이건 언제쓰는거지 공부해보자~~
    public ProductResponseDto createProduct(ProductRequestDto dto) {

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 판매자입니다."));

        // 판매자 권한 확인
        if (!member.isSeller()) {
            throw new IllegalArgumentException("상품은 판매자만 등록할 수 있습니다.");
        }

        Product product = dto.toEntity(member); // dto를 실제 엔티티로 변환
        Product saved = productRepository.save(product); // 변환된 엔티티를 데이터베이스에 저장
        return new ProductResponseDto(saved.getId(), saved.getName(), saved.getPrice(), saved.getStock(), saved.getDescription()); // 응답
    }

    // 모든 상품 가져오기
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::fromEntity)
                .toList();
    }

    // 특정 상품 가져오기
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        return ProductResponseDto.fromEntity(product);
    }

    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductUpdateRequestDto dto) {
        // 판매자 조회
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 판매자가 존재하지 않습니다."));

        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        // 판매자 권한 확인
        if (!product.getSeller().getId().equals(member.getId())) {
            throw new IllegalArgumentException("본인의 상품만 수정할 수 있습니다.");
        }

        // 상품 수정
        product.update(dto.getName(), dto.getPrice(), dto.getStock(), dto.getDescription());

        return ProductResponseDto.fromEntity(product);
    }    @Transactional
    public void deleteProduct(Long productId, ProductDeleteRequestDto dto) {
        // 판매자 조회
        Member seller = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("판매자를 찾을 수 없습니다."));

        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 권한 확인
        if (!product.getSeller().getId().equals(seller.getId())) {
            throw new IllegalArgumentException("본인의 상품만 삭제할 수 있습니다.");
        }

        // 삭제
        productRepository.delete(product);
    }

}
