package com.deu.marketplace.domain.chatLog.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatLog extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
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

        this.chatRoom.getLogs().add(this);
    }

//    @Builder(builderClassName = "bySenderId", builderMethodName = "bySenderId")
//    public ChatLog(ChatRoom chatRoom, String content, Long senderId) {
//        this.chatRoom = chatRoom;
//        if (chatRoom.getRequestedMember().getId() == senderId) {
//            this.sender = chatRoom.getRequestedMember();
//            this.recipient = chatRoom.getItem().getMember();
//        } else {
//            this.sender = chatRoom.getItem().getMember();
//            this.recipient = chatRoom.getRequestedMember();
//        }
//        this.content = content;
//        this.isRead = false;
//
//        this.chatRoom.getLogs().add(this);
//    }

    public void readChatLog() {
        if (!isRead) isRead = true;
    }
}
