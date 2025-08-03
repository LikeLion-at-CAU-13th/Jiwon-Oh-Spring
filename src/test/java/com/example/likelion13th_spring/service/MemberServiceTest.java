package com.example.likelion13th_spring.service;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.enums.Role;
import com.example.likelion13th_spring.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll(); // 기존 것 영향 x

        IntStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder() // 객체 만들기
                    .name("user" + i)
                    .email("user" + i + "@test.com")
                    .address("서울시 테스트동 " + i + "번지")
                    .phoneNumber("010-1234-56" + String.format("%02d", i))
                    .age(i)
                    .deposit(1000 * i)
                    .isAdmin(false)
                    .role(Role.BUYER)
                    .build();

            memberRepository.save(member); // member 저장!
        });
    }

    @Test //테스트 어노테이션
    void testGetMembersByPage() {
        Page<Member> page = memberService.getMembersByPage(0, 10);

        assertThat(page.getContent()).hasSize(10); // 10 개로 나눠
        assertThat(page.getTotalElements()).isEqualTo(30); // 총 30개를
        assertThat(page.getTotalPages()).isEqualTo(3); // 3 페이지
        assertThat(page.getContent().get(0).getName()).isEqualTo("user1");
    }

    @Test
    void testGetMembersSortedByName() {
        Page<Member> page = memberService.getMembersSortedByName(0, 10);

        assertThat(page.getContent()).hasSize(10);
        assertThat(page.getTotalElements()).isEqualTo(11);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getContent().get(0).getName()).isEqualTo("user20");
    }

    @Test
    void testGetMembersByNamePrefix() {
        var result = memberService.getMembersByNamePrefix("user1");

        assertThat(result.size()).isEqualTo(11);
        assertThat(result.get(2).getName()).startsWith("user11");
    }

}
