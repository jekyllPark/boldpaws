package com.dangdang.boldpaws.member.service;

import com.dangdang.boldpaws.member.domain.entity.Member;
import com.dangdang.boldpaws.member.domain.repository.MemberRepository;
import com.dangdang.boldpaws.member.exception.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void signUp(Member member) {
        if (isDuplicated(member.getEmail())) {
            throw new DuplicateMemberException();
        }
        memberRepository.save(member);
    }
    public boolean isDuplicated(String email) {
        return memberRepository.existsByEmail(email);
    }
}
