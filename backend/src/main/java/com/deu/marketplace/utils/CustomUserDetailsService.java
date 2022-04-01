package com.deu.marketplace.utils;

import com.deu.marketplace.config.UserPrincipal;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow();
        return UserPrincipal.byMemberWithNoAttributesBuilder()
                .member(member)
                .build();
    }

    public UserDetails loadUserById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        return UserPrincipal.byMemberWithNoAttributesBuilder()
                .member(member)
                .build();
    }

    public UserDetails loadUserByRefreshToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow();
        return UserPrincipal.byMemberWithNoAttributesBuilder()
                .member(member)
                .build();
    }
}
