package com.deu.marketplace.query.chatRoom.dto;

import com.deu.marketplace.domain.deal.entity.DealState;
import com.deu.marketplace.web.chatRoom.dto.ItemInfo;
import com.deu.marketplace.web.chatRoom.dto.LogInfo;
import com.deu.marketplace.web.chatRoom.dto.MemberInfo;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomViewDto {
    private Long chatRoomId;
    private ItemInfo itemInfo;
    private MemberInfo savedItemMemberInfo;
    private MemberInfo requestedMemberInfo;
    private LogInfo lastLogInfo;

    @QueryProjection
    public ChatRoomViewDto(Long chatRoomId, String itemImg, Long itemId,
                           String title, DealState dealState, Long itemSavedMemberId,
                           String itemSavedMemberNickname, Long requestedMemberId, String requestedMemberNickname,
                           String lastLogContent, LocalDateTime lastModifiedTime) {
        this.chatRoomId = chatRoomId;
        this.itemInfo = ItemInfo.builder()
                .itemImg(itemImg)
                .itemId(itemId)
                .title(title)
                .dealState(dealState)
                .build();
        this.savedItemMemberInfo = MemberInfo.builder()
                .memberId(itemSavedMemberId)
                .nickname(itemSavedMemberNickname)
                .build();
        this.requestedMemberInfo = MemberInfo.builder()
                .memberId(requestedMemberId)
                .nickname(requestedMemberNickname)
                .build();
        this.lastLogInfo = LogInfo.builder()
                .content(lastLogContent)
                .lastModifiedDate(lastModifiedTime)
                .build();
    }

    public void imgToImgUrl(String url) {
        this.itemInfo.imgToImgUrl(url);
    }
}
