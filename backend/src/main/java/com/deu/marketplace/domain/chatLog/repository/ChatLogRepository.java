package com.deu.marketplace.domain.chatLog.repository;

import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {

    @Query(value = "select cl " +
            "from ChatLog cl left join cl.chatRoom cr" +
            " left join cl.sender sm" +
            " left join fetch cl.recipient rm " +
            "where cl.chatRoom.id = :chatRoomId " +
            "and cl.lastModifiedDate < :enterTime",
            countQuery = "select count(cl.id) " +
                    "from ChatLog cl " +
                    "where cl.chatRoom.id = :chatRoomId " +
                    "and cl.lastModifiedDate < :enterTime")
    Page<ChatLog> findByChatRoomId(@Param("chatRoomId") Long chatRoomId,
                                   @Param("enterTime") LocalDateTime enterTime,
                                   Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query(value = "update ChatLog cl set cl.isRead = true " +
            "where cl.chatRoom.id = :chatRoomId and cl.recipient.id = :memberId " +
            "and cl.lastModifiedDate < :now")
    int bulkUpdateRead(@Param("chatRoomId") Long chatRoomId,
                       @Param("memberId") Long memberId,
                       @Param("now") LocalDateTime now);
}
