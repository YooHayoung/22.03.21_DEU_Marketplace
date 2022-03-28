package com.deu.marketplace.domain.chatRoom.repository;

import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {
    @Query("select cr " +
            "from ChatRoom cr " +
            "where cr.item.id = :itemId and cr.requestedMember.id = :memberId")
    Optional<ChatRoom> findByMemberIdAndItemId(@Param("itemId") Long itemId,
                                                      @Param("memberId") Long memberId);

    @EntityGraph(attributePaths = {"item.member", "requestedMember"})
    @Query("select cr from ChatRoom cr where cr.id = :chatRoomId")
    Optional<ChatRoom> findChatRoomByIdFetch(@Param("chatRoomId") Long chatRoomId);

}
