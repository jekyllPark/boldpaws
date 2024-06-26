package com.boldpaws.member.controller;

import com.boldpaws.common.exception.dto.ApiResult;
import com.boldpaws.member.constants.MemberApiMessage;
import com.boldpaws.member.dto.SignUpRequest;
import com.boldpaws.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(MemberApiMessage.SUCCESS_SIGN_UP));
    }
}
