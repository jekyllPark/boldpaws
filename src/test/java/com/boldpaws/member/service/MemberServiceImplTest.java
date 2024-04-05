package com.boldpaws.member.service;

import com.boldpaws.member.domain.entity.Member;
import com.boldpaws.member.domain.repository.MemberRepository;
import com.boldpaws.member.fixture.MemberFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MemberServiceImplTest {
    @InjectMocks
    private MemberServiceImpl memberService;
    @Mock
    private MemberRepository memberRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        memberService = new MemberServiceImpl(memberRepository);
    }

    @Test
    @DisplayName("회원가입에 성공할 경우, memberRepository의 save가 1회 호출 된다.")
    void 회원가입_성공() {
        // given
        Member member = MemberFixture.VALID_MEMBER;

        // when
        memberService.signUp(member);

        // then
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    @DisplayName("이미 등록된 이메일로 가입 시에 isDuplicated는 true가 반환된다.")
    void 회원가입_중복() {
        // given
        Mockito.when(memberService.isDuplicated(any(String.class))).thenReturn(true);

        // when
        boolean isDuplicated = memberService.isDuplicated("someEmail");

        // then
        Assertions.assertEquals(isDuplicated, true);
    }
}