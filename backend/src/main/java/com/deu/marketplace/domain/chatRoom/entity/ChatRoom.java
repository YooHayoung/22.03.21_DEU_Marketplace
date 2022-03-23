package com.deu.marketplace.domain.chatRoom.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member requestedMember;

    @Column(nullable = false)
    private String socket;

    @Builder
    public ChatRoom(Item item, Member requestedMember, String socket) {
        this.item = item;
        this.requestedMember = requestedMember;
        this.socket = socket;
    }
}
