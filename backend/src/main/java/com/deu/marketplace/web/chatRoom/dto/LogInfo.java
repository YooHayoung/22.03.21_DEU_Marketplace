package com.deu.marketplace.web.chatRoom.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class LogInfo {
    private String content;
    private String lastModifiedDate;
    private Long notReadNum;

    @Builder
    public LogInfo(String content, LocalDateTime lastModifiedDate, Long notReadNum) {
        this.content = content;
        this.lastModifiedDate = lastModifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.notReadNum = notReadNum;
    }

    public void setNotReadNum(Long notReadNum) {
        this.notReadNum = notReadNum;
    }
}
