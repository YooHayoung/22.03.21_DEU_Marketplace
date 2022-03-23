package com.deu.marketplace.domain.post.repository;

import com.deu.marketplace.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
