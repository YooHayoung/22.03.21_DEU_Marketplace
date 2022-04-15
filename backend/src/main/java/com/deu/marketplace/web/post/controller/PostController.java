package com.deu.marketplace.web.post.controller;

import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.common.PostSearchCond;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.domain.post.entity.Post;
import com.deu.marketplace.domain.post.service.PostService;
import com.deu.marketplace.domain.postComment.entity.PostComment;
import com.deu.marketplace.domain.postComment.service.PostCommentService;
import com.deu.marketplace.domain.postImg.entity.PostImg;
import com.deu.marketplace.domain.postImg.service.PostImgService;
import com.deu.marketplace.query.postListView.dto.PostDetailViewDto;
import com.deu.marketplace.query.postListView.dto.PostImgDto;
import com.deu.marketplace.query.postListView.dto.PostListViewDto;
import com.deu.marketplace.query.postListView.repository.PostViewRepository;
import com.deu.marketplace.s3.S3Uploader;
import com.deu.marketplace.web.itemImg.dto.ItemImgResponseDto;
import com.deu.marketplace.web.post.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostViewRepository postViewRepository;
    private final PostService postService;
    private final MemberService memberService;
    private final S3Uploader s3Uploader;
    private final PostImgService postImgService;
    private final PostCommentService postCommentService;

    @GetMapping
    public ApiResponse getPostList(PostSearchCond cond,
                                   @PageableDefault(size = 20, page = 0,
                                                 sort = "lastModifiedDate",
                                                 direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostListViewDto> postsPages = postViewRepository.getPostsPages(cond, pageable);
        for (PostListViewDto postsPage : postsPages) {
            if (postsPage.isImgExist()) {
                postsPage.imgFileToImgUrl(s3Uploader.toUrl(postsPage.getFirstImg()));
            }
        }
        return ApiResponse.success("result", postsPages);
    }

    @GetMapping("/{postId}")
    public ApiResponse getOnePost(@PathVariable("postId") Long postId,
                                  @AuthenticationPrincipal Long memberId) {
        PostDetailViewDto postDetailViewDto =
                postViewRepository.getPostDetail(postId, memberId).orElseThrow();
        for (PostImgDto postImg : postDetailViewDto.getPostImgs()) {
            postImg.imgToImgUrl(s3Uploader.toUrl(postImg.getImgFile()));
        }

        return ApiResponse.success("result", postDetailViewDto);
    }

    @PatchMapping("/{postId}")
    public ApiResponse updateOnePost(@PathVariable("postId") Long postId,
                                     @RequestBody PostSaveRequestDto requestDto,
                                     @AuthenticationPrincipal Long memberId) throws ValidationException {
        Post post = postService.updatePost(postId, requestDto.toEntity(), memberId);

        return ApiResponse.success("result", post.getId());
    }

    @DeleteMapping("/{postId}")
    public ApiResponse deleteOnePost(@PathVariable("postId") Long postId,
                                     @AuthenticationPrincipal Long memberId) throws ValidationException {
        List<PostImg> postImgs = postImgService.getAllByPostId(postId);
        s3Uploader.fileDelete(postImgs.stream().map(PostImg::getImgFile).collect(Collectors.toList()));
        postService.deletePost(postId, memberId); // 연관 엔티티 모두 삭제

        return ApiResponse.success("result", null);
    }

    @PostMapping("/save")
    public ApiResponse savePost(@RequestBody PostSaveRequestDto requestDto,
                                @AuthenticationPrincipal Long memberId) {
        Member member = memberService.getMemberById(memberId).orElseThrow();
        Post post = postService.savePost(requestDto.toEntity(member));

        return ApiResponse.success("result", post.getId());
    }
}
