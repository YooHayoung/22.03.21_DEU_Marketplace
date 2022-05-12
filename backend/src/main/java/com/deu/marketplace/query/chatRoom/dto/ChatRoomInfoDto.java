package com.deu.marketplace.query.chatRoom.dto;

import com.deu.marketplace.domain.deal.entity.Deal;
import com.deu.marketplace.domain.deal.entity.DealState;
import com.deu.marketplace.web.chatRoom.dto.MemberInfo;
import com.deu.marketplace.web.item.dto.MemberShortInfoDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomInfoDto {
    private Long chatRoomId;
    private ItemInfo itemInfo;
    private MemberInfo itemSavedMemberInfo;
    private MemberInfo requestedMemberInfo;
    private Long myId;
    private DealInfo dealInfo;

    @QueryProjection
    @Builder
    public ChatRoomInfoDto(Long chatRoomId, Long itemId, String itemImg, String title, int price,
                           DealState dealState, Long itemSavedMemberId, String itemSavedMemberNickname,
                           Long requestedMemberId, String requestedMemberNickname, Deal deal) {
        this.chatRoomId = chatRoomId;
        this.itemInfo = ItemInfo.builder()
                .itemId(itemId)
                .itemImg(itemImg)
                .title(title)
                .price(price)
                .dealState(dealState)
                .build();
        this.itemSavedMemberInfo = MemberInfo.builder()
                .memberId(itemSavedMemberId)
                .nickname(itemSavedMemberNickname)
                .build();
        this.requestedMemberInfo = MemberInfo.builder()
                .memberId(requestedMemberId)
                .nickname(requestedMemberNickname)
                .build();
        if (deal != null) {
            this.dealInfo = DealInfo.builder()
                    .deal(deal)
                    .build();
        }
    }

    public void setMyId(Long myId) {
        this.myId = myId;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ItemInfo {
        private Long itemId;
        private String itemImg;
        private String title;
        private int price;
        private DealState dealState;

        @Builder
        public ItemInfo(Long itemId, String itemImg, String title, int price, DealState dealState) {
            this.itemId = itemId;
            this.itemImg = itemImg;
            this.title = title;
            this.price = price;
            this.dealState = dealState;
        }

        public void imgToImgUrl(String url) {
            this.itemImg = url;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DealInfo {
        private Long dealId;
        private String appointmentDate;
        private String meetingPlace;
        private MemberInfo dealTargetMemberInfo;

        @Builder
        public DealInfo(Deal deal) {
                this.dealId = deal.getId();
                this.appointmentDate = deal.getAppointmentDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                this.meetingPlace = deal.getMeetingPlace();
                this.dealTargetMemberInfo = MemberInfo
                        .builder()
                        .memberId(deal.getTargetMember().getId())
                        .nickname(deal.getTargetMember().getNickname())
                        .build();
        }
    }
}
