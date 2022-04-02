package com.deu.marketplace.domain.postComment.entity;

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
public class PostComment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;

    @Column(nullable = false)
    private String content;

    @Builder
    public PostComment(Post post, Member writer, String content) {
        this.post = post;
        this.writer = writer;
        this.content = content;
    }

    public void updateComment(String content){
        this.content = content;
    }
}
