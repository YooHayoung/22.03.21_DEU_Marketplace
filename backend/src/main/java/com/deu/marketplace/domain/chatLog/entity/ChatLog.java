package com.deu.marketplace.domain.chatLog.entity;

import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, updatable = false, insertable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, updatable = false, insertable = false)
    private Member recipient;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isRead;

    @Builder
    public ChatLog(ChatRoom chatRoom, Member sender, Member recipient, String content) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.isRead = false;
    }

    public void readChatLog() {
        if (!isRead) isRead = true;
    }
}
