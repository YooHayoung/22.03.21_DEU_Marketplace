package com.deu.marketplace.query.chatRoom.dto;

import com.deu.marketplace.domain.deal.entity.Deal;
import com.deu.marketplace.domain.deal.entity.DealState;
import com.deu.marketplace.web.chatRoom.dto.ItemInfo;
import com.deu.marketplace.web.chatRoom.dto.LogInfo;
import com.deu.marketplace.web.chatRoom.dto.MemberInfo;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
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
    private DealInfo dealInfo;

    @QueryProjection
    public ChatRoomViewDto(Long chatRoomId, String itemImg, Long itemId,
                           String title, DealState dealState, Long itemSavedMemberId,
                           String itemSavedMemberNickname, Long requestedMemberId, String requestedMemberNickname,
                           String lastLogContent, LocalDateTime lastModifiedTime, Deal deal) {
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
        if (deal != null) {
            this.dealInfo = DealInfo.builder()
                    .deal(deal)
                    .build();
        }
    }

    public void imgToImgUrl(String url) {
        this.itemInfo.imgToImgUrl(url);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DealInfo {
        private Long dealId;
        private MemberInfo dealTargetMemberInfo;

        @Builder
        public DealInfo(Deal deal) {
            this.dealId = deal.getId();
            this.dealTargetMemberInfo = MemberInfo.builder()
                    .memberId(deal.getTargetMember().getId())
                    .nickname(deal.getTargetMember().getNickname())
                    .build();
        }
    }
}
