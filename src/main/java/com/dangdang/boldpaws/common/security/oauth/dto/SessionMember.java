package com.dangdang.boldpaws.common.security.oauth.dto;

import com.dangdang.boldpaws.member.domain.entity.Member;
import lombok.Getter;

@Getter
public class SessionMember {
    private String name;
    private String email;
    private String picture;

    public SessionMember(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPassword();
    }
}
