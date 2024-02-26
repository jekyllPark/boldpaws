package com.dangdang.boldpaws.member.service;

import com.dangdang.boldpaws.member.domain.repository.MemberRepository;
import com.dangdang.boldpaws.member.dto.SignUpRequest;
import com.dangdang.boldpaws.member.exception.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequest req) {
        if (isDuplicated(req.getEmail())) {
            throw new DuplicateMemberException();
        }
        memberRepository.save(SignUpRequest.toEntity(req, passwordEncoder));
    }
    public boolean isDuplicated(String email) {
        return memberRepository.existsByEmail(email);
    }
}
