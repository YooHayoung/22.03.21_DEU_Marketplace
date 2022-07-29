package com.deu.marketplace.config;

import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import com.deu.marketplace.domain.itemCategory.repository.ItemCategoryRepository;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TestConfig {

    private final ItemCategoryRepository itemCategoryRepository;
    private final MemberRepository memberRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void dataInit() {
        memberRepository.save(Member.ByMemberBuilder()
                .name("임시")
                .oauthId("asd")
                .email("email@email.com")
                .univEmail("univEmail@email.com")
                .build());
        memberRepository.save(Member.ByMemberBuilder()
                .name("임시2")
                .oauthId("asd2")
                .email("email2@email.com")
                .univEmail("univEmail2@email.com")
                .build());

        itemCategoryRepository.save(ItemCategory.builder()
                .categoryName("대학 교재")
                .build());

        itemCategoryRepository.save(ItemCategory.builder()
                .categoryName("서적")
                .build());
        itemCategoryRepository.save(ItemCategory.builder()
                .categoryName("전자 기기")
                .build());
    }
}
