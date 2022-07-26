package com.deu.marketplace.domain.review.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import com.deu.marketplace.domain.deal.entity.Deal;
import com.deu.marketplace.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deal_id", nullable = false)
    private Deal deal;

    private int grade;

    private String comments;

    @Builder
    public Review(Member writer, Deal deal, int grade, String comments) {
        this.writer = writer;
        this.deal = deal;
        this.grade = grade;
        this.comments = comments;
    }
}
