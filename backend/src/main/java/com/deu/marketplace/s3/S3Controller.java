package com.deu.marketplace.s3;

import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.service.ItemService;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import com.deu.marketplace.domain.itemImg.service.ItemImgService;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.domain.post.entity.Post;
import com.deu.marketplace.domain.post.service.PostService;
import com.deu.marketplace.domain.postImg.entity.PostImg;
import com.deu.marketplace.domain.postImg.service.PostImgService;
import com.deu.marketplace.web.itemImg.dto.ItemImgDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/img")
public class S3Controller {

    private final ItemService itemService;
    private final PostService postService;
    private final ItemImgService itemImgService;
    private final PostImgService postImgService;
    private final S3Uploader s3Uploader;

    @PostMapping("/itemImg/upload")
    public ApiResponse uploadItemImg(@RequestParam("itemId") Long itemId,
                                     @RequestPart(value = "file") List<MultipartFile> multipartFiles,
                                     @AuthenticationPrincipal Long memberId)
            throws FileUploadFailedException, EmptyFileException {

        return uploadItemImgFiles(itemId, multipartFiles, memberId);
    }

    @PatchMapping("/itemImg/upload")
    public ApiResponse updateItemImgs(ItemImgUpdateRequestDto requestDto,
                                      @AuthenticationPrincipal Long memberId)
            throws FileUploadFailedException, EmptyFileException {
        List<ItemImg> itemImgs = itemImgService.getAllByItemId(requestDto.getItemId());
        List<ItemImg> delImgs = itemImgs.stream().filter(itemImg -> requestDto.getDelImgs().stream()
                .map(delImg -> delImg.getImgId()).collect(Collectors.toList()).contains(itemImg.getId()))
                .collect(Collectors.toList());
        List<ItemImg> origImgs = new ArrayList<>();
            origImgs = itemImgs.stream().filter(itemImg -> requestDto.getOrigImgs().stream()
                            .map(origImg -> origImg.getImgId()).collect(Collectors.toList()).contains(itemImg.getId()))
                    .collect(Collectors.toList());

        List<String> findImgFiles =
                delImgs.stream().map(ItemImg::getImgFile).collect(Collectors.toList());
        s3Uploader.fileDelete(findImgFiles);
        itemImgService.deleteByImgIdList(delImgs);
        List<ItemImg> updatedItemImgs = new ArrayList<>();
        updatedItemImgs = itemImgService.updateImgSeq(origImgs);


        return uploadItemImgFiles(requestDto.getItemId(), requestDto.getFiles(), memberId, origImgs.size());

//        return ApiResponse.success("result", requestDto.getItemId());
    }

    private ApiResponse uploadItemImgFiles(@RequestParam("itemId") Long itemId,
                                           @RequestPart("file") List<MultipartFile> multipartFiles,
                                           @AuthenticationPrincipal Long memberId)
            throws FileUploadFailedException, EmptyFileException {
        Item item = itemService.getOneItemById(itemId).orElseThrow();
        List<String> imgFileNames = s3Uploader.upload(multipartFiles, "item", itemId, memberId);
        List<ItemImg> itemImgs = toItemImgEntity(imgFileNames, item);

        List<ItemImg> results = itemImgService.saveAll(itemImgs);

        return ApiResponse.success("result", itemId);
    }
    private ApiResponse uploadItemImgFiles(@RequestParam("itemId") Long itemId,
                                           @RequestPart("file") List<MultipartFile> multipartFiles,
                                           @AuthenticationPrincipal Long memberId,
                                           int origImgSize)
            throws FileUploadFailedException, EmptyFileException {
        Item item = itemService.getOneItemById(itemId).orElseThrow();
        List<String> imgFileNames = s3Uploader.upload(multipartFiles, "item", itemId, memberId);
        List<ItemImg> itemImgs = toItemImgEntity(imgFileNames, item, origImgSize);

        List<ItemImg> results = itemImgService.saveAll(itemImgs);

        return ApiResponse.success("result", itemId);
    }

    @GetMapping("/itemImg")
    public ApiResponse getItemImgs(@RequestParam("itemId") Long itemId) {

        List<ItemImg> itemImgs = itemImgService.getAllByItemId(itemId);
        List<String> imgUrls =
                s3Uploader.toUrl(itemImgs.stream()
                        .map(itemImg -> itemImg.getImgFile()).collect(Collectors.toList()));


        return ApiResponse.success("result", imgUrls);
    }

    @PostMapping("/postImg/upload")
    public ApiResponse uploadPostImg(@RequestParam("postId") Long postId,
                                     @RequestPart(value = "file") List<MultipartFile> multipartFiles,
                                     @AuthenticationPrincipal Long memberId)
            throws FileUploadFailedException, EmptyFileException {

        return uploadPostImgFiles(postId, multipartFiles, memberId);
    }

    @PatchMapping("/postImg/upload")
    public ApiResponse updatePostImgs(@RequestParam("postId") Long postId,
                                      @RequestPart(value = "file") List<MultipartFile> multipartFiles,
                                      @AuthenticationPrincipal Long memberId) throws FileUploadFailedException, EmptyFileException {

        List<String> findImgFiles =
                postImgService.getAllByPostId(postId).stream().map(PostImg::getImgFile).collect(Collectors.toList());
        s3Uploader.fileDelete(findImgFiles);
        postImgService.deleteAllByPostId(postId);

        return uploadPostImgFiles(postId, multipartFiles, memberId);
    }

    private ApiResponse uploadPostImgFiles(@RequestParam("postId") Long postId,
                                           @RequestPart("file") List<MultipartFile> multipartFiles,
                                           @AuthenticationPrincipal Long memberId) throws FileUploadFailedException, EmptyFileException {
        Post post = postService.getOnePostByPostId(postId).orElseThrow();
        List<String> imgFileNames = s3Uploader.upload(multipartFiles, "post", postId, memberId);
        List<PostImg> postImgs = toPostImgEntity(imgFileNames, post);

        List<PostImg> results = postImgService.saveAll(postImgs);

        return ApiResponse.success("result", postId);
    }

    @GetMapping("/postImg")
    public ApiResponse getPostImgs(@RequestParam("postId") Long postId) {

        List<PostImg> postImgs = postImgService.getAllByPostId(postId);
        List<String> imgUrls =
                s3Uploader.toUrl(postImgs.stream()
                        .map(postImg -> postImg.getImgFile()).collect(Collectors.toList()));

        return ApiResponse.success("result", imgUrls);
    }


    private List<ItemImg> toItemImgEntity(List<String> imgNames, Item item) {
        List<ItemImg> itemImgs = new ArrayList<>();
        int i = 0;
        for (String imgName : imgNames) {
            itemImgs.add(ItemImg.builder()
                    .item(item)
                    .imgFile(imgName)
                    .imgSeq(++i)
                    .build());
        }
        return itemImgs;
    }
    private List<ItemImg> toItemImgEntity(List<String> imgNames, Item item, int origImgSize) {
        List<ItemImg> itemImgs = new ArrayList<>();
        int i = origImgSize;
        for (String imgName : imgNames) {
            itemImgs.add(ItemImg.builder()
                    .item(item)
                    .imgFile(imgName)
                    .imgSeq(++i)
                    .build());
        }
        return itemImgs;
    }
    private List<PostImg> toPostImgEntity(List<String> imgNames, Post post) {
        List<PostImg> postImgs = new ArrayList<>();
        int i = 0;
        for (String imgName : imgNames) {
            postImgs.add(PostImg.builder()
                    .post(post)
                    .imgFile(imgName)
                    .imgSeq(++i)
                    .build());
        }
        return postImgs;
    }
}
