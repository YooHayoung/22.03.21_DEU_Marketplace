package com.deu.marketplace.web.item.controller;

import com.deu.marketplace.common.response.ApiResponse;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.service.ItemService;
import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import com.deu.marketplace.domain.itemCategory.service.ItemCategoryService;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import com.deu.marketplace.domain.lecture.service.LectureService;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.utils.fileUploader.FileUploader;
import com.deu.marketplace.web.item.dto.ItemSaveRequestDto;
import com.deu.marketplace.web.item.dto.ItemUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ItemService itemService;
    private final ItemCategoryService itemCategoryService;
    private final LectureService lectureService;
    private final FileUploader fileUploader;

    private final MemberService memberService;

    // 상품 등록
    @PostMapping("/save")
    public ApiResponse<?> save(@Validated ItemSaveRequestDto itemSaveRequestDto,
                               @RequestPart(value = "file") List<MultipartFile> multipartFiles) {
        // TODO Member -> 로그인 처리
        Member member = memberService.findById(1L).orElseThrow();

        // itemCategory : 강의 교재, 서적, 기타
        // 카테고리에 맞추어 Item 변환.
        // 카테고리 없으면 예외 발생
        ItemCategory itemCategory = itemCategoryService.findById(itemSaveRequestDto.getItemCategoryId()).orElseThrow();
        Item item = null;
        if (itemCategory.getCategoryName().equals("강의 교재")) {
            // 카테고리:강의교재 인데 강의 없으면 예외 발생
            item = itemSaveRequestDto.toEntity(member, itemCategory, lectureService.findById(itemSaveRequestDto.getLectureId()).orElseThrow());
        } else {
            item = itemSaveRequestDto.toEntity(member, itemCategory);
        }

        // key:origFileName, value:storeFileName
        // fileUploader를 통해 파일 저장 -> 기존 파일명과 저장된 파일명 반환.
        Map<String, String> uploadFileNames = fileUploader.uploadFiles(multipartFiles);
        int i = 0;
        for (String origFileName : uploadFileNames.keySet()) {
            // ItemImg 엔티티로 변환하면서 Item에 추가.
            ItemImg.builder()
                    .item(item)
                    .origFileName(origFileName)
                    .storeFileName(uploadFileNames.get(origFileName))
                    .imgSeq(++i)
                    .build();
        }

        // Item + ItemImgList 저장
        Item savedItem = itemService.save(item);

        return ApiResponse.success("itemId", savedItem.getId());
    }

    // 상품 수정
    @PatchMapping("/update/{itemId}")
    public ApiResponse<?> updateOneItem(@PathVariable("itemId") Long itemId,
                                        @Validated ItemUpdateRequestDto requestDto,
                                        @RequestPart(value = "file") List<MultipartFile> multipartFiles) {
        // TODO Member -> 로그인 처리
        Member member = memberService.findById(1L).orElseThrow();

        ItemCategory itemCategory = itemCategoryService.findById(requestDto.getItemCategoryId()).orElseThrow();
        Item itemUpdateInfo = null;
        if (itemCategory.getCategoryName().equals("강의 교재")) {
            // 카테고리:강의교재 인데 강의 없으면 예외 발생
            itemUpdateInfo = requestDto.toEntity(itemCategory, lectureService.findById(requestDto.getLectureId()).orElseThrow());
        } else {
            itemUpdateInfo = requestDto.toEntity(itemCategory);
        }

        // 상품 검색. 삭제된 상품일 경우 NoSuchElementException 예외 발생.
        Item item = itemService.findByIdAndMember(itemId, member).orElseThrow();
        // 삭제할 이미지 ID 목록을 통해 삭제할 이미지의 저장 파일 이름 추출.
        List<String> storeFileNamesForDelete = item.getItemImgs().stream()
                .filter(itemImg -> requestDto.getDelImgIdList().contains(itemImg.getId()))
                .map(ItemImg::getStoreFileName)
                .collect(Collectors.toList());
        // FileUploader를 통해 파일 삭제.
        fileUploader.deleteFiles(storeFileNamesForDelete);

        // 추가 이미지 파일 업로드 -> 파일 이름 반환
        Map<String, String> uploadFileNames = fileUploader.uploadFiles(multipartFiles);
        for (String origFileName : uploadFileNames.keySet()) {
            // ItemImg 엔티티로 변환하면서 Item에 추가.
            ItemImg.builder()
                    .item(item)
                    .origFileName(origFileName)
                    .storeFileName(uploadFileNames.get(origFileName))
                    .build();
        }

        // 상품, 상품 수정 정보, 삭제할 이미지 저장 파일명을 넘겨 상품 수정.
        // 내부에서 이미지 삭제, 이미지 순서 update
        Item updatedItem = itemService.update(item, itemUpdateInfo, storeFileNamesForDelete);

        return ApiResponse.success("itemId", updatedItem.getId());
    }

    // TODO 상품 삭제
    @DeleteMapping("/delete/{itemId}")
    public ApiResponse<?> deleteOneItem(@PathVariable("itemId") Long itemId) {
        // TODO Member -> 로그인 처리
        Member member = memberService.findById(2L).orElseThrow();

        boolean deleted = itemService.delete(member, itemId);

        return ApiResponse.success("result", deleted);
    }
}
