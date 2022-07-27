package com.deu.marketplace.domain.member.service;

import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findById(Long itemId) {
        return memberRepository.findById(itemId);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    public void updateNickname(Member member, String nickname) {
        member.updateNickname(nickname);
    }

    @Transactional
    public void withdrawal(Member member, boolean deleted) {
        member.withdrawal(deleted);
    }

    // TODO 학생 인증 기능 구현
}
