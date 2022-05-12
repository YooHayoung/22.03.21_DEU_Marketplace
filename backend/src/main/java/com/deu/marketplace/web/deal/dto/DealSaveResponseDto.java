package com.deu.marketplace.web.deal.dto;

import com.deu.marketplace.domain.deal.entity.Deal;
import com.deu.marketplace.query.chatRoom.dto.ChatRoomInfoDto;
import com.deu.marketplace.web.chatRoom.dto.MemberInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DealSaveResponseDto {
    private String dealState;
    private ChatRoomInfoDto.DealInfo dealInfo;

    @Builder
    public DealSaveResponseDto(Deal deal) {
        this.dealState = deal.getDealState().name();
        this.dealInfo = ChatRoomInfoDto.DealInfo.builder().deal(deal).build();
    }

//    @Builder
//        public DealSaveResponseDto(Deal deal) {
//            this.dealId = deal.getId();
//            this.appointmentDate = deal.getAppointmentDateTime()
//                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//            this.meetingPlace = deal.getMeetingPlace();
//            this.dealTargetMemberInfo = MemberInfo
//                    .builder()
//                    .memberId(deal.getTargetMember().getId())
//                    .nickname(deal.getTargetMember().getNickname())
//                    .build();
//        }
}
