package com.deu.marketplace.domain.postRecommend.repository;

import com.deu.marketplace.domain.post.entity.Post;
import com.deu.marketplace.domain.postRecommend.entity.PostRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRecommendRepository extends JpaRepository<PostRecommend, Long> {

    @Query(value = "select pr from PostRecommend pr where pr.post.id = :postId and pr.member.id = :memberId")
    Optional<PostRecommend> findByInfo(@Param("postId") Long postId, @Param("memberId") Long memberId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from PostRecommend pr where pr.post = :post")
    void deleteByPost(@Param("post") Post post);
}
