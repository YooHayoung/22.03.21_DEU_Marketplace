package com.deu.marketplace.domain.chatRoom.repository;

import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepositoryCustom {
    List<ChatRoom> findChatRoomsByMemberId(Long memberId);

    Optional<ChatRoom> findChatRoomByIdAndMemberId(Long chatRoomId, Long memberId);
}
