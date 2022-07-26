package com.deu.marketplace.domain.member.repository;

import com.deu.marketplace.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUnivEmail(String univEmail);

    Optional<Member> findByEmail(String email);
}
