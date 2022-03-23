package com.deu.marketplace.domain.postRecommend.repository;

import com.deu.marketplace.domain.postRecommend.entity.PostRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRecommendRepository extends JpaRepository<PostRecommend, Long> {
}
