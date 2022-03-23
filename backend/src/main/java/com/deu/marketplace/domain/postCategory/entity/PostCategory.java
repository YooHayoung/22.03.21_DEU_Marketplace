package com.deu.marketplace.domain.postCategory.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_category_id")
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @Builder
    public PostCategory(String categoryName) {
        Assert.notNull(categoryName, "categoryName must not be null");
        this.categoryName = categoryName;
    }
}
