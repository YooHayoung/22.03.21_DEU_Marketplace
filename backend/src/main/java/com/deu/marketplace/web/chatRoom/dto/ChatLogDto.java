package com.deu.marketplace.web.chatRoom.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatLogDto {
    private Long chatLogId;
    private Long senderId;
    private Long recipientId;
    private String content;
    private LocalDateTime lastModifiedTime;
    private boolean isRead;

    @Builder
    public ChatLogDto(Long chatLogId, Long senderId, Long recipientId,
                      String content, LocalDateTime lastModifiedTime, boolean isRead) {
        this.chatLogId = chatLogId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.lastModifiedTime = lastModifiedTime;
        this.isRead = isRead;
    }
}
