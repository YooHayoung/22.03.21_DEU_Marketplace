package com.deu.marketplace.domain.post.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.postCategory.entity.PostCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id", nullable = false)
    private PostCategory postCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Builder
    public Post(PostCategory postCategory, Member writer, String title, String content) {
        this.postCategory = postCategory;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public void updatePost(PostCategory postCategory, String title, String content) {
        this.postCategory = postCategory;
        this.title = title;
        this.content = content;
    }
}
