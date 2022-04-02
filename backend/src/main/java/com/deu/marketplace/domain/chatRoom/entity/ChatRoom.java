package com.deu.marketplace.domain.chatRoom.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    @JoinColumn(name = "requested_member_id", nullable = false)
    private Member requestedMember;

    @Column(nullable = false)
    private String socket;

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatLog> logs = new ArrayList<>();

//    @Builder
//    public ChatRoom(Item item, Member requestedMember, String socket) {
//        this.item = item;
//        this.requestedMember = requestedMember;
//        this.socket = socket;
//    }

    @Builder
    public ChatRoom(Item item, Member requestedMember) {
        this.item = item;
        this.requestedMember = requestedMember;
        this.socket = UUID.randomUUID().toString();
    }
}
