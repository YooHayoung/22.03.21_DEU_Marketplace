package com.deu.marketplace.domain.chatRoom.repository;

import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
