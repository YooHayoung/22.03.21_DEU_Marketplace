package com.deu.marketplace.domain.chatLog.service.impl;

import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import com.deu.marketplace.domain.chatLog.repository.ChatLogRepository;
import com.deu.marketplace.domain.chatLog.service.ChatLogService;
import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatLogServiceImpl implements ChatLogService {

    private final ChatLogRepository chatLogRepository;

    @Override
    public Page<ChatLog> getChatLogPage(Long chatRoomId, LocalDateTime enterTime, Pageable pageable) {
        return chatLogRepository.findByChatRoomId(chatRoomId, enterTime, pageable);
    }

    @Override
    @Transactional
    public ChatLog saveChatLog(ChatRoom chatRoom, Member sender, Member recipient, String message) {
        ChatLog chatLog = ChatLog.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .recipient(recipient)
                .content(message)
                .build();
        return chatLogRepository.save(chatLog);
    }

    @Override
    @Transactional
    public int bulkUpdateRead(Long chatRoomId, Long memberId, LocalDateTime now) {
        return chatLogRepository.bulkUpdateRead(chatRoomId, memberId, now);
    }

    @Override
    public void deleteAllChatLogByChatRoomId(Long ChatRoomId) {

    }
}
