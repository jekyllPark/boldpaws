package com.dangdang.boldpaws.member.controller;

import com.dangdang.boldpaws.member.dto.SignUpRequest;
import com.dangdang.boldpaws.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController implements MemberControllerSpec {
    private final MemberService memberService;
    @Override
    public ResponseEntity<HttpStatus> signUp(SignUpRequest req) {
        memberService.signUp(req);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
