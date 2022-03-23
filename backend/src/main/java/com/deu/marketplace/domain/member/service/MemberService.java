package com.deu.marketplace.domain.member.service;

import com.deu.marketplace.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Optional<Member> getMemberById(Long id);

    Optional<Member> getMemberByEmail(String email);

    List<Member> getMembersByNickname(String nickname);

    Member saveMember(Member member);

    Member updateMemberNickname(Long id, String nickname);
}
