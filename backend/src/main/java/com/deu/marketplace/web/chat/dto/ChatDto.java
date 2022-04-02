package com.deu.marketplace.web.chat.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatDto {
    private Long roomId;
    private Long senderId;
    private String message;

    @Builder
    public ChatDto(Long roomId, Long senderId, String message) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
