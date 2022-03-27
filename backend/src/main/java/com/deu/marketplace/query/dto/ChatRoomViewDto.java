package com.deu.marketplace.query.dto;

import com.deu.marketplace.domain.deal.entity.DealState;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomViewDto {
    private Long chatRoomId;
    private String itemImg;
    private Long itemId;
    private String title;
    private DealState dealState;
    private Long itemSavedMemberId;
    private String itemSavedMemberNickname;
    private Long requestedMemberId;
    private String requestedMemberNickname;
    private String lastLogContent;
    private LocalDateTime lastModifiedTime;

    @QueryProjection
    public ChatRoomViewDto(Long chatRoomId, String itemImg, Long itemId,
                           String title, DealState dealState, Long itemSavedMemberId,
                           String itemSavedMemberNickname, Long requestedMemberId, String requestedMemberNickname,
                           String lastLogContent, LocalDateTime lastModifiedTime) {
        this.chatRoomId = chatRoomId;
        this.itemImg = itemImg;
        this.itemId = itemId;
        this.title = title;
        this.dealState = dealState;
        this.itemSavedMemberId = itemSavedMemberId;
        this.itemSavedMemberNickname = itemSavedMemberNickname;
        this.requestedMemberId = requestedMemberId;
        this.requestedMemberNickname = requestedMemberNickname;
        this.lastLogContent = lastLogContent;
        this.lastModifiedTime = lastModifiedTime;
    }
}
