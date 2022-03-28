package com.deu.marketplace.query.repository;

import com.deu.marketplace.domain.chatLog.entity.QChatLog;
import com.deu.marketplace.domain.chatRoom.entity.QChatRoom;
import com.deu.marketplace.domain.deal.entity.DealState;
import com.deu.marketplace.domain.item.entity.QItem;
import com.deu.marketplace.domain.member.entity.QMember;
import com.deu.marketplace.query.dto.ChatRoomInfoDto;
import com.deu.marketplace.query.dto.ChatRoomViewDto;
import com.deu.marketplace.query.dto.QChatRoomInfoDto;
import com.deu.marketplace.query.dto.QChatRoomViewDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

import static com.deu.marketplace.domain.chatLog.entity.QChatLog.chatLog;
import static com.deu.marketplace.domain.chatRoom.entity.QChatRoom.chatRoom;
import static com.deu.marketplace.domain.deal.entity.QDeal.deal;
import static com.deu.marketplace.domain.item.entity.QItem.item;
import static com.deu.marketplace.domain.itemImg.entity.QItemImg.itemImg;
import static com.deu.marketplace.domain.member.entity.QMember.member;

@Repository
public class ChatRoomViewRepository {

    private final JPAQueryFactory queryFactory;

    public ChatRoomViewRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//    SELECT *
//    FROM (SELECT chat_room.chat_room_id, item_img.img_file, item.item_id,
//          item.title, item_deal.deal_state, item.member_id, chat_room.requested_member_id,
//          chat_log.content, chat_log.last_modified_date
//                  FROM chat_room
//                  LEFT JOIN member m1 ON chat_room.requested_member_id = m1.member_id
//                  LEFT JOIN item ON chat_room.item_id = item.item_id
//                  LEFT JOIN member m2 ON item.member_id = m2.member_id
//                  LEFT JOIN item_img ON item.item_id = item_img.item_id
//                  LEFT JOIN item_deal ON item.item_id = item_deal.item_id
//                  LEFT JOIN chat_log ON chat_room.chat_room_id = chat_log.chat_room_id
//                  WHERE (item_img.img_seq = 1
//                  OR item_img.img_seq IS NULL)
//    AND chat_log.last_modified_date IN (SELECT MAX(chat_log.last_modified_date) FROM chat_log GROUP BY chat_log.chat_room_id)) AS a
//    LEFT join
//            (select chat_room.chat_room_id, COUNT(chat_log.chat_log_id)
//    FROM chat_room
//    LEFT JOIN chat_log ON chat_log.chat_room_id = chat_room.chat_room_id AND chat_log.is_read = 0
//    GROUP BY chat_room.chat_room_id ) AS b ON a.chat_room_id = b.chat_room_id
//    ORDER BY a.last_modified_date desc;

    public Page<ChatRoomViewDto> getChatRoomPages(Long memberId, Pageable pageable) {
        List<ChatRoomViewDto> content = queryFactory
                .select(new QChatRoomViewDto(
                        chatRoom.id,
                        itemImg.imgFile,
                        item.id,
                        item.title,
                        deal.dealState,
                        item.member.id,
                        item.member.nickname,
                        chatRoom.requestedMember.id,
                        chatRoom.requestedMember.nickname,
                        chatLog.content,
                        chatLog.lastModifiedDate
                ))
                .from(chatRoom)
                .leftJoin(chatRoom.requestedMember, new QMember("requestedMember"))
                .leftJoin(chatRoom.item, item)
                .leftJoin(item.member, member)
                .leftJoin(itemImg).on(item.eq(itemImg.item))
                .leftJoin(deal).on(item.eq(deal.item))
                .leftJoin(chatLog).on(chatRoom.eq(chatLog.chatRoom))
                .fetchJoin()
                .where(chatRoom.requestedMember.id.eq(memberId)
                        .or(chatRoom.item.member.id.eq(memberId)),
                        (itemImg.imgSeq.eq(1).or(itemImg.imgSeq.isNull()))
                        .and(chatLog.lastModifiedDate.in(JPAExpressions
                                .select(chatLog.lastModifiedDate.max())
                                .from(chatLog)
                                .groupBy(chatLog.chatRoom))))
                .orderBy(chatLog.lastModifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(chatRoom.count())
                .from(chatRoom)
                .leftJoin(item).on(item.eq(chatRoom.item).and(item.member.eq(chatRoom.item.member)))
                .leftJoin(member).on(member.eq(chatRoom.requestedMember))
                .where(chatRoom.requestedMember.id.eq(memberId)
                                .or(chatRoom.item.member.id.eq(memberId)));


        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    public Map<Long, Long> getNotReadCounts(List<ChatRoomViewDto> chatRoomViewDtos, Long memberId) {
        List<Long> chatRoomIds = new ArrayList<>();
        for (ChatRoomViewDto chatRoomViewDto : chatRoomViewDtos) {
            chatRoomIds.add(chatRoomViewDto.getChatRoomId());
        }
        List<Tuple> result = queryFactory
                .select(chatRoom.id,
                        chatLog.id.count())
                .from(chatRoom)
                .leftJoin(chatLog).on(chatLog.chatRoom.eq(chatRoom).and(chatLog.isRead.eq(false)))
                .where(chatRoom.id.in(chatRoomIds))
                .groupBy(chatRoom.id)
                .fetch();

        Map<Long, Long> map = new HashMap<>();
        for (Tuple tuple : result) {
            Long chatRoomId = tuple.get(0, Long.class);
            Long notReadNum = tuple.get(1, Long.class);
            map.put(chatRoomId, notReadNum);
        }
        return map;
    }

//    Long chatRoomId, Long itemId, String itemImg, String title, int price,
//    DealState dealState, Long itemSavedMemberId, String itemSavedMemberNickname,
//    Long requestedMemberId, String requestedMemberNickname
    public Optional<ChatRoomInfoDto> getChatRoomInfo(Long chatRoomId, Long memberId) {
        return Optional.ofNullable(queryFactory
                .select(new QChatRoomInfoDto(
                        chatRoom.id,
                        item.id,
                        itemImg.imgFile,
                        item.title,
                        item.price,
                        deal.dealState,
                        item.member.id,
                        item.member.nickname,
                        chatRoom.requestedMember.id,
                        chatRoom.requestedMember.nickname
                ))
                .from(chatRoom)
                .leftJoin(chatRoom.requestedMember, new QMember("requestedMember"))
                .leftJoin(chatRoom.item, item)
                .leftJoin(item.member, member)
                .leftJoin(itemImg).on(item.eq(itemImg.item))
                .leftJoin(deal).on(item.eq(deal.item))
                .fetchJoin()
                .where(chatRoom.requestedMember.id.eq(memberId)
                                .or(chatRoom.item.member.id.eq(memberId)),
                        (itemImg.imgSeq.eq(1).or(itemImg.imgSeq.isNull())),
                        chatRoom.id.eq(chatRoomId))
                .fetchOne());
    }
}
