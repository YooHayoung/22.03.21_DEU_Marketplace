package com.deu.marketplace.domain.memberRefreshToken.service;

import com.deu.marketplace.domain.memberRefreshToken.entity.MemberRefreshToken;
import com.deu.marketplace.domain.memberRefreshToken.repository.MemberRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRefreshTokenService {

    private final MemberRefreshTokenRepository memberRefreshTokenRepository;

    public Optional<MemberRefreshToken> findByMemberId(Long memberId) {
        return memberRefreshTokenRepository.findByMemberId(memberId);
    }
    public Optional<MemberRefreshToken> findByRefreshToken(String refreshToken) {
        return memberRefreshTokenRepository.findByRefreshToken(refreshToken);
    }
    public Optional<MemberRefreshToken> findByMemberIdAndRefreshToken(Long memberId, String refreshToken) {
        return memberRefreshTokenRepository.findByMemberIdAndRefreshToken(memberId, refreshToken);
    }

    @Transactional
    public void deleteByMemberId(Long memberId) {
        memberRefreshTokenRepository.deleteByMemberId(memberId);
    }
}
