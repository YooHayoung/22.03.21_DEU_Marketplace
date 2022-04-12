package com.deu.marketplace.web.deal.dto;

import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.deal.entity.Deal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DealSaveRequestDto {
    private Long chatRoomId;
    private String appointmentDate;
    private String meetingPlace;

    public DealSaveRequestDto(Long chatRoomId, String appointmentDate, String meetingPlace) {
        this.chatRoomId = chatRoomId;
        this.appointmentDate = appointmentDate;
        this.meetingPlace = meetingPlace;
    }

    // 물품 등록자가 요청한게 아니면 빈 Deal 반환.
    public Optional<Deal> toEntity(ChatRoom chatRoom, Long memberId) {
        if (chatRoom.getItem().getMember().getId() == memberId) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime =
                    LocalDateTime.parse(appointmentDate.replace("_", " "), formatter);
            return Optional.ofNullable(Deal.dtoToEntityBuilder()
                    .chatRoom(chatRoom)
                    .appointmentDateTime(dateTime)
                    .meetingPlace(meetingPlace)
                    .build());
        } else {
            return Optional.empty();
        }
    }
}
