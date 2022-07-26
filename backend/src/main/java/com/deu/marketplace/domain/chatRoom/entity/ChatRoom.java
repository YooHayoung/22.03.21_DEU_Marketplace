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
    private Boolean registerOut;

    @Column(nullable = false)
    private Boolean requesterOut;

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatLog> logs = new ArrayList<>();

    @Builder
    public ChatRoom(Item item, Member requestedMember) {
        this.item = item;
        this.requestedMember = requestedMember;
        this.registerOut = false;
        this.requesterOut = false;
    }
}
