package com.deu.marketplace.domain.item.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import com.deu.marketplace.domain.lecture.entity.Lecture;
import com.deu.marketplace.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_category_id", nullable = false)
    private ItemCategory itemCategory;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    // Embedded Type은 저장할 때 적절한 검증을 통해 모든 필드 값이 null로 저장되지 않도록 하는 것이 좋다. <- 꺼내쓸때 너무 힘들다..
    @Embedded
    private BookState bookState;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean deleted;

    //@Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImg> itemImgs = new ArrayList<>();

    @Builder(builderClassName = "ByUnivBookBuilder", builderMethodName = "ByUnivBookBuilder")
    public Item(Member writer, ItemCategory itemCategory,
                String title, Lecture lecture, BookState bookState,
                int price, String description) {
        this.writer = writer;
        this.itemCategory = itemCategory;
        this.title = title;
        this.lecture = lecture;
        this.bookState = bookState;
        this.price = price;
        this.description = description;
        this.deleted = false;
    }

    @Builder(builderClassName = "ByNormalItemBuilder", builderMethodName = "ByNormalItemBuilder")
    public Item(Member writer, ItemCategory itemCategory, String title,
                int price, String description) {
        this.writer = writer;
        this.itemCategory = itemCategory;
        this.title = title;
        this.price = price;
        this.description = description;
        this.deleted = false;
    }

    @Builder(builderClassName = "ByUnivItemBuilder", builderMethodName = "ByUnivItemBuilder")
    public Item(Member writer, ItemCategory itemCategory, String title,
                Lecture lecture, int price, String description) {
        this.writer = writer;
        this.itemCategory = itemCategory;
        this.title = title;
        this.lecture = lecture;
        this.price = price;
        this.description = description;
        this.deleted = false;
    }

    @Builder(builderClassName = "ByBookItemBuilder", builderMethodName = "ByBookItemBuilder")
    public Item(Member writer, ItemCategory itemCategory, String title, BookState bookState,
                int price, String description) {
        this.writer = writer;
        this.itemCategory = itemCategory;
        this.title = title;
        this.bookState = bookState;
        this.price = price;
        this.description = description;
        this.deleted = false;
    }

    public void updateItem(Item updateItemInfo) {
        this.itemCategory = updateItemInfo.itemCategory;
        this.title = updateItemInfo.title;
        this.lecture = updateItemInfo.lecture;
        this.bookState = updateItemInfo.bookState;
        this.price = updateItemInfo.price;
        this.description = updateItemInfo.description;
    }

    // Item 삭제 표기
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
