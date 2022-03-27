package com.deu.marketplace.domain.chatRoom.repository;

import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.chatRoom.entity.QChatRoom;
import com.deu.marketplace.domain.item.entity.QItem;
import com.deu.marketplace.domain.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.deu.marketplace.domain.chatRoom.entity.QChatRoom.chatRoom;
import static com.deu.marketplace.domain.item.entity.QItem.item;
import static com.deu.marketplace.domain.member.entity.QMember.member;

public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ChatRoomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ChatRoom> findChatRoomsByMemberId(Long memberId) {
        List<ChatRoom> result = queryFactory
                .select(chatRoom)
                .from(chatRoom)
                .leftJoin(chatRoom.item, item)
                .leftJoin(item.member, member)
                .where(chatRoom.requestedMember.id.eq(memberId).or(item.member.id.eq(memberId)))
                .fetch();
        return null;
    }
}
