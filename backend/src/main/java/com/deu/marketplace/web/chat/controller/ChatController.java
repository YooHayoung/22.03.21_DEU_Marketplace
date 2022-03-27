package com.deu.marketplace.web.chat.controller;

import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.chatRoom.service.ChatRoomService;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.service.ItemService;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
//    - 채팅방 목록 조회 : GET - /api/v1/chat
//- 채팅방 생성 : POST - /api/v1/chat/create
//- 채팅방 입장 : GET - /api/v1/chat/{roomId} - 본인 토큰으로 인증
//- 채팅 전송 : POST - /api/v1/chat/{roomId}
//- → 웹 소켓으로 하는거 다시

    private final SimpMessagingTemplate simpMessagingTemplate;

}
