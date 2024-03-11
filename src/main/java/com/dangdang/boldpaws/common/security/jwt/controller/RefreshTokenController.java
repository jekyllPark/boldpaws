package com.dangdang.boldpaws.common.security.jwt.controller;

import com.dangdang.boldpaws.common.exception.dto.ApiResult;
import com.dangdang.boldpaws.common.security.jwt.component.TokenProvider;
import com.dangdang.boldpaws.common.security.jwt.constants.TokenType;
import com.dangdang.boldpaws.common.security.jwt.dto.AccessTokenResponse;
import com.dangdang.boldpaws.common.security.jwt.service.RefreshTokenService;
import com.dangdang.boldpaws.common.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.dangdang.boldpaws.common.exception.dto.ApiResult.success;

@RequiredArgsConstructor
@RestControllerAdvice
@RequestMapping("/refreshToken")
public class RefreshTokenController implements RefreshTokenControllerSpec {
    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;

    @Override
    public ResponseEntity<ApiResult> createAccessToken(HttpServletRequest request) {
        String refreshToken = CookieUtils.get(request, TokenType.REFRESH_TOKEN.getName());
        if (!tokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        AccessTokenResponse accessToken = refreshTokenService.createAccessToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(success(accessToken));
    }
}
