package com.deu.marketplace.domain.memberRefreshToken.repository;

import com.deu.marketplace.domain.memberRefreshToken.entity.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {
    Optional<MemberRefreshToken> findByMemberId(Long memberId);
    Optional<MemberRefreshToken> findByRefreshToken(String refreshToken);
    Optional<MemberRefreshToken> findByMemberIdAndRefreshToken(Long memberId, String refreshToken);
    void deleteByMemberId(Long memberId);
}
