package com.dangdang.boldpaws.common.security.jwt.service;

import com.dangdang.boldpaws.common.security.jwt.component.TokenProvider;
import com.dangdang.boldpaws.common.security.jwt.constants.JwtErrorMessage;
import com.dangdang.boldpaws.common.security.jwt.domain.entity.RefreshToken;
import com.dangdang.boldpaws.common.security.jwt.domain.repository.RefreshTokenRepository;
import com.dangdang.boldpaws.common.security.jwt.dto.AccessTokenResponse;
import com.dangdang.boldpaws.member.constants.MemberErrorMessage;
import com.dangdang.boldpaws.member.domain.entity.Member;
import com.dangdang.boldpaws.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    @Transactional
    @Override
    public void save(Member member, String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByMember(member).orElse(new RefreshToken(member, token));
        refreshTokenRepository.save(refreshToken);
    }
    @Transactional
    @Override
    public void delete(String refreshToken) {
        String email = tokenProvider.findClaimsByJwt(refreshToken).getSubject();
        Member findMember = memberRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException(MemberErrorMessage.NOT_FOUND_USER_EMAIL));
        RefreshToken findToken = refreshTokenRepository.findByMember(findMember).orElseThrow(() -> new NoSuchElementException(JwtErrorMessage.NOT_FOUND_REFRESH_TOKEN));
        refreshTokenRepository.delete(findToken);
    }
    @Override
    public AccessTokenResponse createAccessToken(String refreshToken) {
        String email = tokenProvider.findClaimsByJwt(refreshToken).getSubject();
        Member findMember = memberRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException(MemberErrorMessage.NOT_FOUND_USER_EMAIL));
        String accessToken = tokenProvider.createToken(findMember.getEmail());
        return new AccessTokenResponse(accessToken);
    }
}
