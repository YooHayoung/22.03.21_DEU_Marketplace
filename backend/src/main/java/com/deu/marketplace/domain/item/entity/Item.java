package com.deu.marketplace.domain.item.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import com.deu.marketplace.domain.lecture.entity.Lecture;
import com.deu.marketplace.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_category_id", nullable = false)
    private ItemCategory itemCategory;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Embedded
    private BookState bookState;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private Classification classification;

    @Builder(builderClassName = "ByUnivBookBuilder", builderMethodName = "ByUnivBookBuilder")
    public Item(Member member, ItemCategory itemCategory,
                String title,Lecture lecture, BookState bookState,
                int price, String description, Classification classification) {
        this.member = member;
        this.itemCategory = itemCategory;
        this.title = title;
        this.lecture = lecture;
        this.bookState = bookState;
        this.price = price;
        this.description = description;
        this.classification = classification;
    }

    @Builder(builderClassName = "ByNormalItemBuilder", builderMethodName = "ByNormalItemBuilder")
    public Item(Member member, ItemCategory itemCategory, String title,
                int price, String description, Classification classification) {
        this.member = member;
        this.itemCategory = itemCategory;
        this.title = title;
        this.price = price;
        this.description = description;
        this.classification = classification;
    }

    @Builder(builderClassName = "ByUnivItemBuilder", builderMethodName = "ByUnivItemBuilder")
    public Item(Member member, ItemCategory itemCategory, String title,
                Lecture lecture, int price, String description, Classification classification) {
        this.member = member;
        this.itemCategory = itemCategory;
        this.title = title;
        this.lecture = lecture;
        this.price = price;
        this.description = description;
        this.classification = classification;
    }

    @Builder(builderClassName = "ByBookItemBuilder", builderMethodName = "ByBookItemBuilder")
    public Item(Member member, ItemCategory itemCategory, String title, BookState bookState,
                int price, String description, Classification classification) {
        this.member = member;
        this.itemCategory = itemCategory;
        this.title = title;
        this.bookState = bookState;
        this.price = price;
        this.description = description;
        this.classification = classification;
    }

    public void updateUnivBookItemInfo(ItemCategory itemCategory, String title, Lecture lecture,
                                       BookState bookState, int price,String description) {
        this.itemCategory = itemCategory;
        this.title = title;
        this.lecture = lecture;
        this.bookState = bookState;
        this.price = price;
        this.description = description;
    }

    public void updateNormalItemInfo(ItemCategory itemCategory,
                                     String title, int price,String description) {
        this.itemCategory = itemCategory;
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public void updateUnivItemInfo(ItemCategory itemCategory, String title,
                                   Lecture lecture, int price,String description) {
        this.itemCategory = itemCategory;
        this.title = title;
        this.lecture = lecture;
        this.price = price;
        this.description = description;
    }

    public void updateBookItemInfo(ItemCategory itemCategory, String title,
                                   BookState bookState, int price,String description) {
        this.itemCategory = itemCategory;
        this.title = title;
        this.bookState = bookState;
        this.price = price;
        this.description = description;
    }

    public boolean isLectureInfoExist() {
        return (lecture != null);
    }
}
