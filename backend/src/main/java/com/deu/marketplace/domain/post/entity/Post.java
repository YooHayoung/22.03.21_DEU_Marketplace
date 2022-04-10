package com.deu.marketplace.domain.post.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.postCategory.entity.PostCategory;
import com.deu.marketplace.domain.postImg.entity.PostImg;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImg> postImgs = new ArrayList<>();

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

    public void clearPostImgs() {
        this.postImgs.clear();
    }

    public void validWriterIdAndMemberId(Long memberId) throws ValidationException {
        if (this.writer.getId() != memberId)
            throw new ValidationException("Member is not the same as writer");
    }
}
