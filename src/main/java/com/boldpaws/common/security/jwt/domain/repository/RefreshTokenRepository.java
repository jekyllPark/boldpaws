package com.boldpaws.common.security.jwt.domain.repository;

import com.boldpaws.common.security.jwt.domain.entity.RefreshToken;
import com.boldpaws.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
}
