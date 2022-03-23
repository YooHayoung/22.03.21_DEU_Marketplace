package com.deu.marketplace.domain.itemImg.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import com.deu.marketplace.domain.item.entity.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImg extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private String imgFile;

    @Column(nullable = false)
    private int imgSeq;

    @Builder
    public ItemImg(Item item, String imgFile, int imgSeq) {
        this.item = item;
        this.imgFile = imgFile;
        this.imgSeq = imgSeq;
    }

    public void updateSeq(int seq) {
        this.imgSeq = seq;
    }
}
