package com.deu.marketplace.domain.postComment.service;

import com.deu.marketplace.domain.postComment.entity.PostComment;
import com.deu.marketplace.domain.postComment.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;

    public Page<PostComment> getPostCommentPage(Long postId, Pageable pageable){
        return postCommentRepository.findByPostId(postId, pageable);
    }

    @Transactional
    public void deletePostComment(Long postCommentId, Long memberId) throws ValidationException {
        PostComment postComment = postCommentRepository.findById(postCommentId).orElseThrow();
        postComment.validWriterIdAndMemberId(memberId);
        postCommentRepository.delete(postComment);
    }

    @Transactional
    public PostComment savePostComment(PostComment postComment) {
        return postCommentRepository.save(postComment);
    }
}
