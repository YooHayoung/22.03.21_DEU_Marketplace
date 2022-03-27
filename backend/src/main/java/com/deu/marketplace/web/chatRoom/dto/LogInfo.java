package com.deu.marketplace.web.chatRoom.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LogInfo {
    private String content;
    private LocalDateTime lastModifiedTime;
    private Long notReadNum;

    @Builder
    public LogInfo(String content, LocalDateTime lastModifiedTime, Long notReadNum) {
        this.content = content;
        this.lastModifiedTime = lastModifiedTime;
        this.notReadNum = notReadNum;
    }
}
