package com.deu.marketplace.domain.postRecommend.service;

import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.post.entity.Post;
import com.deu.marketplace.domain.postRecommend.entity.PostRecommend;
import com.deu.marketplace.domain.postRecommend.repository.PostRecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostRecommendService {

    private final PostRecommendRepository postRecommendRepository;

    public Optional<PostRecommend> updatePostRecommend(Post post, Member member) {
        Optional<PostRecommend> postRecommend = postRecommendRepository.findByInfo(post.getId(), member.getId());
        if (postRecommend.isPresent()) {
            postRecommendRepository.delete(postRecommend.orElseThrow());
            return Optional.empty();
        } else {
            PostRecommend savedPostRecommend =
                    postRecommendRepository.save(PostRecommend.builder().post(post).member(member).build());
            return Optional.ofNullable(savedPostRecommend);
        }
    }
}
