package com.deu.marketplace.domain.chatRoom.service.impl;

import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.chatRoom.repository.ChatRoomRepository;
import com.deu.marketplace.domain.chatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    @Transactional
    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public Optional<ChatRoom> getOneChatRoomByItemIdAndMemberId(Long itemId, Long memberId) {
        return chatRoomRepository.findByMemberIdAndItemId(itemId, memberId);
    }

    @Override
    public Optional<ChatRoom> getOneChatRoomByRoomId(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }
}
