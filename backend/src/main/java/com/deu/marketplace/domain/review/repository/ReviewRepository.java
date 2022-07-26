package com.deu.marketplace.domain.review.repository;

import com.deu.marketplace.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
