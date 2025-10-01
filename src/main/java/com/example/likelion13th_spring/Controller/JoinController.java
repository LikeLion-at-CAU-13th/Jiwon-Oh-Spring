package com.example.likelion13th_spring.Controller;

import com.example.likelion13th_spring.dto.request.JoinRequestDto;
import com.example.likelion13th_spring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final MemberService memberService;

    @PostMapping("/join")
    public void join(@RequestBody JoinRequestDto joinRequestDto) {
        memberService.join(joinRequestDto);
    }
}
