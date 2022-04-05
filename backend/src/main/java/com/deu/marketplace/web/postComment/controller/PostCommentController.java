package com.deu.marketplace.web.postComment.controller;

import com.deu.marketplace.domain.postComment.entity.PostComment;
import com.deu.marketplace.domain.postComment.service.PostCommentService;
import com.deu.marketplace.web.postComment.dto.PostCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/postComments")
public class PostCommentController {

//    - 목록 조회 : GET - /api/v1/comment?페이징조건
//- 댓글 작성 : POST - /api/v1/comment/save
//- 댓글 수정 : PATCH - /api/v1/comment/{commentId}
//- 댓글 삭제 : DELETE - /api/v1/comment/{commentId}

    private final PostCommentService postCommentService;

    @GetMapping
    public ResponseEntity<?> getPostCommentPage(@RequestParam("postId") Long postId,
                                                @AuthenticationPrincipal Long memberId,
                                                @PageableDefault(size = 20, page = 0,
                                                    sort = "createdDate",
                                                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostCommentDto> postCommentPage =
                postCommentService.getPostCommentPage(postId, pageable).map(PostCommentDto::new);

        return ResponseEntity.ok().body(postCommentPage);
    }
}
