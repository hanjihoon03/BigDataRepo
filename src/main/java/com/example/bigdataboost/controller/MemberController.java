package com.example.bigdataboost.controller;

import com.example.bigdataboost.dto.MemberLoginRequestDto;
import com.example.bigdataboost.jwt.TokenInfo;
import com.example.bigdataboost.model.Member;
import com.example.bigdataboost.model.UserEntity;
import com.example.bigdataboost.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;
    }
    @PostMapping("/signIn")
    public ResponseEntity<Member> createUser(@RequestBody Member member) {
        Member savedMember = memberService.saveUser(member);
        return ResponseEntity.ok(savedMember);
    }
    @PostMapping("/testToken")
    public String test() {
        return "success";
    }
}
