package com.deu.marketplace.web.deal.controller;

import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.chatRoom.service.ChatRoomService;
import com.deu.marketplace.domain.deal.entity.Deal;
import com.deu.marketplace.domain.deal.service.DealService;
import com.deu.marketplace.web.deal.dto.DealResponseDto;
import com.deu.marketplace.web.deal.dto.DealSaveRequestDto;
import com.deu.marketplace.web.deal.dto.DealSaveResponseDto;
import com.deu.marketplace.web.deal.dto.DealUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deal")
public class DealController {

    private final DealService dealService;
    private final ChatRoomService chatRoomService;

    @PostMapping("/new")
    public ApiResponse createDeal(@RequestBody DealSaveRequestDto requestDto,
                                  @AuthenticationPrincipal Long memberId) {
        ChatRoom chatRoom = chatRoomService.getOneChatRoomByRoomId(requestDto.getChatRoomId()).orElseThrow();
        Deal deal = dealService.saveDeal(requestDto.toEntity(chatRoom, memberId).orElseThrow());

//        DealResponseDto dealInfo = DealResponseDto.builder().deal(deal).build();
        DealSaveResponseDto responseDto = DealSaveResponseDto.builder().deal(deal).build();
        return ApiResponse.success("result", responseDto);
    }

    @PatchMapping("/{dealId}/complete")
    public ApiResponse dealComplete(@PathVariable("dealId") Long dealId,
                                       @AuthenticationPrincipal Long memberId) throws ValidationException {
        Deal deal = dealService.dealComplete(dealId, memberId);
        DealResponseDto dealInfo = DealResponseDto.builder().deal(deal).build();

        return ApiResponse.success("result", dealInfo);
    }

    @PatchMapping("/{dealId}/update")
    public ApiResponse dealUpdate(@PathVariable("dealId") Long dealId,
                                    @RequestBody DealUpdateRequestDto dealUpdateRequestDto,
                                    @AuthenticationPrincipal Long memberId) throws ValidationException {
        Deal deal = dealService.updateDeal(dealId,
                dealUpdateRequestDto.getMeetingPlace(),
                dealUpdateRequestDto.getAppointmentDate()).orElseThrow();
        DealResponseDto dealInfo = DealResponseDto.builder().deal(deal).build();

        return ApiResponse.success("result", dealInfo);
    }

    @DeleteMapping("/{dealId}")
    public ApiResponse dealCancel(@PathVariable("dealId") Long dealId,
                                  @AuthenticationPrincipal Long memberId) throws ValidationException {
        Deal deal = dealService.dealCancel(dealId, memberId);

        return ApiResponse.success("result", null);
    }
}
