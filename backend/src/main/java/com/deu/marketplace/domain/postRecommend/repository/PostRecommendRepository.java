package com.deu.marketplace.domain.postRecommend.repository;

import com.deu.marketplace.domain.postRecommend.entity.PostRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRecommendRepository extends JpaRepository<PostRecommend, Long> {

    @Query(value = "select pr from PostRecommend pr where pr.post.id = :postId and pr.member.id = :memberId")
    Optional<PostRecommend> findByInfo(Long postId, Long memberId);
}
