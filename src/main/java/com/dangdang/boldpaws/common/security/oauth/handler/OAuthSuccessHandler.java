package com.dangdang.boldpaws.common.security.oauth.handler;

import com.dangdang.boldpaws.common.security.jwt.component.TokenProvider;
import com.dangdang.boldpaws.common.security.jwt.service.RefreshTokenService;
import com.dangdang.boldpaws.member.constants.MemberErrorMessage;
import com.dangdang.boldpaws.member.domain.entity.Member;
import com.dangdang.boldpaws.member.domain.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final MemberRepository memberRepository;
    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        Member member = findMember(authentication);
        String refreshToken = tokenProvider.createToken(member.getEmail());
        refreshTokenService.save(member, refreshToken);
    }
    private Member findMember(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        return memberRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException(MemberErrorMessage.NOT_FOUND_USER_EMAIL));
    }
}
