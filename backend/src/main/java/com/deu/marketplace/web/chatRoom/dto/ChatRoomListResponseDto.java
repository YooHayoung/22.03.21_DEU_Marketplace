package com.deu.marketplace.web.chatRoom.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomListResponseDto {
    private Long chatRoomId;
    private ItemInfo itemInfo;
    private MemberInfo savedItemMemberInfo;
    private MemberInfo requestedMemberInfo;
    private LogInfo lastLogInfo;

    @Builder
    public ChatRoomListResponseDto(Long chatRoomId, ItemInfo itemInfo, MemberInfo savedItemMemberInfo,
                                   MemberInfo requestedMemberInfo, LogInfo lastLogInfo) {
        this.chatRoomId = chatRoomId;
        this.itemInfo = itemInfo;
        this.savedItemMemberInfo = savedItemMemberInfo;
        this.requestedMemberInfo = requestedMemberInfo;
        this.lastLogInfo = lastLogInfo;
    }
}
