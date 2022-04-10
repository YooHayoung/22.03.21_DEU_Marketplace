package com.deu.marketplace.web.postRecommend.controller;

import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.domain.post.service.PostService;
import com.deu.marketplace.domain.postRecommend.entity.PostRecommend;
import com.deu.marketplace.domain.postRecommend.service.PostRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/postRecommend")
public class PostRecommendController {

    private final MemberService memberService;
    private final PostService postService;
    private final PostRecommendService postRecommendService;

    @GetMapping("/{postId}")
    public ApiResponse setPostRecommend(@PathVariable("postId") Long postId,
                                        @AuthenticationPrincipal Long memberId) {
        Optional<PostRecommend> postRecommend =
                postRecommendService.updatePostRecommend(postService.getOnePostByPostId(postId).orElseThrow(),
                    memberService.getMemberById(memberId).orElseThrow());

        return ApiResponse.success("result", postRecommend.orElse(null).getId());
    }
}
