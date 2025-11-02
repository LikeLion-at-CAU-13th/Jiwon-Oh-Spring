package com.example.likelion13th_spring.Controller;

import com.example.likelion13th_spring.config.PrincipalHandler;
import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.dto.request.JoinRequestDto;
import com.example.likelion13th_spring.dto.response.TokenResponseDto;
import com.example.likelion13th_spring.jwt.JwtTokenProvider;
import com.example.likelion13th_spring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/join")
    public void join(@RequestBody JoinRequestDto joinRequestDto) {
        memberService.join(joinRequestDto);
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody JoinRequestDto joinRequestDto) {
        Member member = memberService.login(joinRequestDto);
        return TokenResponseDto.of(jwtTokenProvider.generateAccessToken(member.getName()),jwtTokenProvider.generateRefreshToken(member.getName()));
    }

    @GetMapping("/my")
    public ResponseEntity<String> getMyName() {
        String name = PrincipalHandler.getUsernameFromPrincipal();
        return ResponseEntity.ok(name);
    }
}
