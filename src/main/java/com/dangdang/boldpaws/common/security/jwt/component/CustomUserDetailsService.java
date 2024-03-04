package com.dangdang.boldpaws.common.security.jwt.component;

import com.dangdang.boldpaws.member.domain.entity.Member;
import com.dangdang.boldpaws.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.dangdang.boldpaws.common.security.jwt.constants.JwtErrorMessage.NOT_FOUND_AUTHENTICATION;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(NOT_FOUND_AUTHENTICATION));
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}
