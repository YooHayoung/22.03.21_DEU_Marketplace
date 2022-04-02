package com.deu.marketplace.domain.chatLog.service.impl;

import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import com.deu.marketplace.domain.chatLog.repository.ChatLogRepository;
import com.deu.marketplace.domain.chatLog.service.ChatLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatLogServiceImpl implements ChatLogService {

    private final ChatLogRepository chatLogRepository;

    @Override
    public Page<ChatLog> getChatLogPage(Long chatRoomId, Pageable pageable) {
        return chatLogRepository.findByChatRoomId(chatRoomId, pageable);
    }

    @Override
    public ChatLog saveChatLog(ChatLog chatLog) {
        return chatLogRepository.save(chatLog);
    }

    @Override
    public void deleteAllChatLogByChatRoomId(Long ChatRoomId) {

    }
}
