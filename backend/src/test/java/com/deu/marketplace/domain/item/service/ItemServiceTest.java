package com.deu.marketplace.domain.item.service;

import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import com.deu.marketplace.domain.lecture.entity.Lecture;
import com.deu.marketplace.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    EntityManager em;

    @BeforeEach
    void init() {
        for (int i = 0; i<10; i++) {
            Member member = Member.ByDefaultNicknameBuilder()
                    .name("member" + i)
                    .email("member" + i + "@email.com")
                    .build();
            em.persist(member);
        }
        for (int i = 0; i<5; i++) {
            ItemCategory itemCategory = ItemCategory.builder()
                    .name("category" + i)
                    .build();
            em.persist(itemCategory);
        }
        for (int i = 0; i<5; i++) {
            Lecture lecture = Lecture.builder()
                    .lectureName("lecture" + i)
                    .professorName("professor" + i)
                    .build();
            em.persist(lecture);
        }
        for (int i = 0; i<50; i++) {
            Item item = Item.builder()
                    .member(em.find(Member.class, 1L))
                    .itemCategory(em.find(ItemCategory.class, 11L))
                    .lecture(em.find(Lecture.class, 16L))
                    .title("물품" + i)
                    .price(1000*i)
                    .description("설명" + i)
                    .classification(Classification.SELL)
                    .build();
            em.persist(item);
        }
    }


}