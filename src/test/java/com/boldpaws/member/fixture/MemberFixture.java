package com.boldpaws.member.fixture;

import com.boldpaws.member.domain.entity.Member;
import com.boldpaws.member.domain.entity.Role;

public class MemberFixture {
    public static Member VALID_MEMBER = new Member(
            1L, "kim@dangdang.com", "asdf123!", "010-1234-5678", "김당당", null, Role.USER
    );
}
