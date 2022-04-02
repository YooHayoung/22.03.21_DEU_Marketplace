package com.deu.marketplace.domain.postImg.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import com.deu.marketplace.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImg extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String imgFile;

    @Column(nullable = false)
    private int imgSeq;

    @Builder
    public PostImg(Post post, String imgFile, int imgSeq) {
        this.post = post;
        this.imgFile = imgFile;
        this.imgSeq = imgSeq;
    }

    public void changeSeq(int seq){
        this.imgSeq = seq;
    }
}
