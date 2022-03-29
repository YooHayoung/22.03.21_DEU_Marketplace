package com.deu.marketplace.web.chatRoom.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomListDto {
    private Long chatRoomId;
    private ItemInfo itemInfo;
    private MemberInfo targetMemberInfo;
    private LogInfo lastLogInfo;

    @Builder
    public ChatRoomListDto(Long chatRoomId, ItemInfo itemInfo, MemberInfo targetMemberInfo, LogInfo lastLogInfo) {
        this.chatRoomId = chatRoomId;
        this.itemInfo = itemInfo;
        this.targetMemberInfo = targetMemberInfo;
        this.lastLogInfo = lastLogInfo;
    }
}
