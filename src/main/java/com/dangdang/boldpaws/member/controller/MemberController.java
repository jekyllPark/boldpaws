package com.dangdang.boldpaws.member.controller;

import com.dangdang.boldpaws.common.exception.dto.ApiResult;
import com.dangdang.boldpaws.member.constants.MemberApiMessage;
import com.dangdang.boldpaws.member.dto.SignUpRequest;
import com.dangdang.boldpaws.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dangdang.boldpaws.common.exception.dto.ApiResult.success;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController implements MemberControllerSpec {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<ApiResult> signUp(SignUpRequest req) {
        memberService.signUp(SignUpRequest.toEntity(req, passwordEncoder));
        return ResponseEntity.status(HttpStatus.OK).body(success(MemberApiMessage.SUCCESS_SIGN_UP));
    }
}
