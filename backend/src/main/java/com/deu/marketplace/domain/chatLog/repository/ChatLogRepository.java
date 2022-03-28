package com.deu.marketplace.domain.chatLog.repository;

import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {

    @Query(value = "select cl " +
            "from ChatLog cl left join cl.chatRoom cr" +
            " left join cl.sender sm" +
            " left join fetch cl.recipient rm " +
            "where cl.chatRoom.id = :chatRoomId",
            countQuery = "select count(cl.id) from ChatLog cl where cl.chatRoom.id = :chatRoomId")
    Page<ChatLog> findByChatRoomId(@Param("chatRoomId") Long chatRoomId, Pageable pageable);
}
