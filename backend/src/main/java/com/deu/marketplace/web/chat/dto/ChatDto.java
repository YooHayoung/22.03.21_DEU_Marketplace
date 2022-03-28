package com.deu.marketplace.web.chat.dto;

import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatDto {
    private Long roomId;
    private Long senderId;
    private Long recipientId;
    private String message;

    @Builder
    public ChatDto(Long roomId, Long senderId, Long recipientId, String message) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatLog toEntity() {
        return ChatLog.builder()
                .build();
    }
}
