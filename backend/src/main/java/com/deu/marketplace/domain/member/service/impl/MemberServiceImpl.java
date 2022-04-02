package com.deu.marketplace.domain.member.service.impl;

import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.repository.MemberRepository;
import com.deu.marketplace.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public List<Member> getMembersByNickname(String nickname) {
        return memberRepository.findByNicknameContains(nickname);
    }

    @Override
    @Transactional
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public Member updateMemberNickname(Long id, String nickname) {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new NoResultException());
        findMember.updateNickname(nickname);
        return findMember;
    }
}
