package com.deu.marketplace.domain.post.service;

import com.deu.marketplace.domain.post.entity.Post;
import com.deu.marketplace.domain.post.repository.PostRepository;
import com.deu.marketplace.domain.postComment.repository.PostCommentRepository;
import com.deu.marketplace.domain.postImg.entity.PostImg;
import com.deu.marketplace.domain.postRecommend.repository.PostRecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final PostRecommendRepository postRecommendRepository;

    public Optional<Post> getOnePostByPostId(Long postId) {
        return  postRepository.findById(postId);
    }

    @Transactional
    public void deletePost(Long postId, Long memberId) throws ValidationException {
        Post post = postRepository.findById(postId).orElseThrow();
        post.validWriterIdAndMemberId(memberId);
        postRecommendRepository.deleteByPost(post);
        postCommentRepository.deleteByPost(post);
        postRepository.delete(post);
    }

    @Transactional
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long postId, Post post, Long memberId) throws ValidationException {
        Post findPost = postRepository.findById(postId).orElseThrow();
        findPost.validWriterIdAndMemberId(memberId);
//        findPost.clearPostImgs();
//        post.getPostImgs().stream().map(postImg -> PostImg.builder()
//                .post(findPost)
//                .imgFile(postImg.getImgFile())
//                .imgSeq(postImg.getImgSeq())
//                .build()).collect(Collectors.toList());
//        System.out.println(findPost.getPostImgs().size());
        findPost.updatePost(post);
        return findPost;
    }
}
