package com.deu.marketplace.domain.postCategory.repository;

import com.deu.marketplace.domain.postCategory.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    Optional<PostCategory> findByCategoryName(String categoryName);
    List<PostCategory> findByCategoryNameContains(String categoryName);
}
