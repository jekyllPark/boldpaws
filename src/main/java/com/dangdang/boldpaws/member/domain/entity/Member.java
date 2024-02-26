package com.dangdang.boldpaws.member.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String hp;
    @Column(nullable = false)
    private String name;
    @Builder
    public Member(Long id, String email, String password, String hp, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.hp = hp;
        this.name = name;
    }
}
