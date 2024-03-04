package com.dangdang.boldpaws.common.security.jwt.service;

import com.dangdang.boldpaws.common.security.jwt.dto.AccessTokenResponse;
import com.dangdang.boldpaws.member.domain.entity.Member;

public interface RefreshTokenService {
    void save(Member member, String token);
    void delete(String refreshToken);
    AccessTokenResponse createAccessToken(String refreshToken);
}
