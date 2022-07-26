package com.deu.marketplace.domain.itemCategory.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_category_id")
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @Builder
    public ItemCategory(String categoryName) {
        Assert.notNull(categoryName, "categoryName must not be null");
        this.categoryName = categoryName;
    }
}
