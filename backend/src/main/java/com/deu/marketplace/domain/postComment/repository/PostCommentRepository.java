package com.deu.marketplace.domain.postComment.repository;

import com.deu.marketplace.domain.postComment.entity.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    @Query("select pc from PostComment pc where pc.post.id = :postId")
    Page<PostComment> findByPostId(@Param("postId") Long postId, Pageable pageable);
}
