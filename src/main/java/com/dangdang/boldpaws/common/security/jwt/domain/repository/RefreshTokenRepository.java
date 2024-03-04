package com.dangdang.boldpaws.common.security.jwt.domain.repository;

import com.dangdang.boldpaws.common.security.jwt.domain.entity.RefreshToken;
import com.dangdang.boldpaws.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
}
