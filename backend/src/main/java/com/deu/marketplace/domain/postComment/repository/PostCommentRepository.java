package com.deu.marketplace.domain.postComment.repository;

import com.deu.marketplace.domain.postComment.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
