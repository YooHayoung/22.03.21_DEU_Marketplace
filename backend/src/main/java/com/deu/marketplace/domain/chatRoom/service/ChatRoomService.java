package com.deu.marketplace.domain.chatRoom.service;

import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;

import java.util.Optional;

public interface ChatRoomService {
    ChatRoom createChatRoom(ChatRoom chatRoom);

    Optional<ChatRoom> getOneChatRoomByItemIdAndMemberId(Long itemId, Long memberId);

    Optional<ChatRoom> getOneChatRoomByRoomId(Long chatRoomId);

    Optional<ChatRoom> getOneToEnterChatRoom(Long chatRoomId, Long memberId);
}
