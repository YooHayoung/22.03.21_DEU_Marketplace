package com.deu.marketplace.domain.post.repository;

import com.deu.marketplace.domain.post.entity.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"writer"})
    Optional<Post> findById(Long id);
}
