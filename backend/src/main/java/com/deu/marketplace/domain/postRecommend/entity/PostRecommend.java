package com.deu.marketplace.domain.postRecommend.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRecommend extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public PostRecommend(Post post, Member member) {
        this.post = post;
        this.member = member;
    }
}
