package com.dangdang.boldpaws.common.security.jwt.domain.entity;

import com.dangdang.boldpaws.member.domain.entity.BaseTimeEntity;
import com.dangdang.boldpaws.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "refresh_token_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;
    @Column(nullable = false)
    private String token;
    public RefreshToken(Member member, String token) {
        this.member = member;
        this.token = token;
    }
}
