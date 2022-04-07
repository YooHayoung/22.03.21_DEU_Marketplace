package com.deu.marketplace.domain.chatLog.service;

import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ChatLogService {
    Page<ChatLog> getChatLogPage(Long chatRoomId, LocalDateTime enterTime, Pageable pageable);

    ChatLog saveChatLog(ChatRoom chatRoom, Member sender, Member recipient, String message);

    int bulkUpdateRead(Long chatRoomId, Long memberId, LocalDateTime now);

    void deleteAllChatLogByChatRoomId(Long ChatRoomId);
}
