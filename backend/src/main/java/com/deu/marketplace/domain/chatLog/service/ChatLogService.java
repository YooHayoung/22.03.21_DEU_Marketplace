package com.deu.marketplace.domain.chatLog.service;

import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatLogService {
    Page<ChatLog> getChatLogPage(Long chatRoomId, Pageable pageable);

    ChatLog saveChatLog(ChatLog chatLog);

    void deleteAllChatLogByChatRoomId(Long ChatRoomId);
}
