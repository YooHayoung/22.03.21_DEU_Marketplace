package com.deu.marketplace.domain.chatRoom.repository;

import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.chatRoom.entity.QChatRoom;
import com.deu.marketplace.domain.item.entity.QItem;
import com.deu.marketplace.domain.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
                .fetchJoin()
                .where(chatRoom.requestedMember.id.eq(memberId).or(item.member.id.eq(memberId)))
                .fetch();
        return result;
    }

    @Override
    public Optional<ChatRoom> findChatRoomByIdAndMemberId(Long chatRoomId, Long memberId) {
        ChatRoom result = queryFactory
                .select(chatRoom)
                .from(chatRoom)
                .leftJoin(chatRoom.requestedMember, new QMember("requestedMember"))
                .leftJoin(chatRoom.item, item)
                .leftJoin(chatRoom.item.member, member)
                .fetchJoin()
                .where(chatRoom.id.eq(chatRoomId),
                        chatRoom.requestedMember.id.eq(memberId).or(chatRoom.item.member.id.eq(memberId)))
                .fetchOne();
        return Optional.ofNullable(result);
    }
}
