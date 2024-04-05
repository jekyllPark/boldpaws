package com.boldpaws.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column
    private String password;
    @Column
    private String hp;
    @Column(nullable = false)
    private String name;
    @Column
    private String picture;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Builder
    public Member(Long id, String email, String password, String hp, String name, String picture, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.hp = hp;
        this.name = name;
        this.picture = picture;
        this.role = role;
    }
    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }
    public Member updatePicture(String picture) {
        this.picture = picture;
        return this;
    }
    public Member updatePassword(String password, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
        return this;
    }
    public Member updatePhoneNumber(String hp) {
        this.hp = hp;
        return this;
    }
    public String getRoleKey() {
        return this.role.getKey();
    }
}
