//package com.dangdang.boldpaws.common.security.jwt.controller;
//
//import com.dangdang.boldpaws.common.exception.dto.ApiResult;
//import com.dangdang.boldpaws.common.security.jwt.component.TokenProvider;
//import com.dangdang.boldpaws.common.security.jwt.dto.AccessTokenResponse;
//import com.dangdang.boldpaws.common.security.jwt.service.RefreshTokenService;
//import io.swagger.v3.oas.annotations.Operation;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import static com.dangdang.boldpaws.common.exception.dto.ApiResult.*;
//
//@RequiredArgsConstructor
//@RestControllerAdvice
//@RequestMapping("/refreshToken")
//public class RefreshTokenController implements RefreshTokenControllerSpec {
//    private final RefreshTokenService refreshTokenService;
//    private final TokenProvider tokenProvider;
//
//    @Override
//    public ResponseEntity<ApiResult> createAccessToken(HttpServletRequest request) {
//        //fixme
////        return ResponseEntity.status(HttpStatus.OK).body(success(new AccessTokenResponse(refreshTokenService.createAccessToken(null))));
//    }
//}
