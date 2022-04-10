package com.deu.marketplace.domain.post.service;

import com.deu.marketplace.domain.post.entity.Post;
import com.deu.marketplace.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public Optional<Post> getOnePostByPostId(Long postId) {
        return  postRepository.findById(postId);
    }
}
