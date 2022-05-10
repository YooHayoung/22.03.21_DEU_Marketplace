package com.deu.marketplace.web.postComment.controller;

import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.domain.post.service.PostService;
import com.deu.marketplace.domain.postComment.entity.PostComment;
import com.deu.marketplace.domain.postComment.service.PostCommentService;
import com.deu.marketplace.web.postComment.dto.PostCommentDto;
import com.deu.marketplace.web.postComment.dto.PostCommentSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
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
    private final PostService postService;
    private final MemberService memberService;

    @GetMapping
    public ApiResponse getPostCommentPage(@RequestParam("postId") Long postId,
                                          @AuthenticationPrincipal Long memberId,
                                          @PageableDefault(size = 20, page = 0,
                                                    sort = "createdDate",
                                                    direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostCommentDto> postCommentPage =
                postCommentService.getPostCommentPage(postId, pageable).map(PostCommentDto::new);

        return ApiResponse.success("result", postCommentPage);
    }

    @PostMapping("/new")
    public ApiResponse savePostComment(@RequestBody PostCommentSaveDto postCommentSaveDto,
                                       @AuthenticationPrincipal Long memberId) {
        PostComment postComment =
                postCommentService.savePostComment(requestInfoToEntity(postCommentSaveDto, memberId));
        PostCommentDto postCommentDto = PostCommentDto.builder()
                .postComment(postComment)
                .build();

        return ApiResponse.success(
                "result",
                postCommentService.getPostCommentPage(postCommentSaveDto.getPostId(),
                        PageRequest.of(0, 20, Sort.Direction.DESC, "createdDate"))
                        .map(PostCommentDto::new));
    }

    @DeleteMapping("/{postCommentId}")
    public ApiResponse deletePostComment(@PathVariable("postCommentId") Long postCommentId,
                                         @AuthenticationPrincipal Long memberId) throws ValidationException {
        postCommentService.deletePostComment(postCommentId, memberId);

        return ApiResponse.success("result", null);
    }

    private PostComment requestInfoToEntity(PostCommentSaveDto postCommentSaveDto, Long memberId) {
        return PostComment.builder()
                .post(postService.getOnePostByPostId(postCommentSaveDto.getPostId()).orElseThrow())
                .content(postCommentSaveDto.getComment())
                .writer(memberService.getMemberById(memberId).orElseThrow())
                .build();
    }
}
