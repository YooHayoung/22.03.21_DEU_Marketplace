package com.deu.marketplace.web.chat.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatLogDto {
    private Long chatLogId;
    private Long senderId;
    private Long recipientId;
    private String message;
    private String lastModifiedDate;
    private boolean read;

    @Builder
    public ChatLogDto(Long chatLogId, Long senderId, Long recipientId,
                      String message,LocalDateTime lastModifiedDate, boolean read) {
        this.chatLogId = chatLogId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.message = message;
        this.lastModifiedDate = lastModifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.read = read;
    }
}
