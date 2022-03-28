package com.deu.marketplace.web.chat.controller;

import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import com.deu.marketplace.domain.chatLog.service.ChatLogService;
import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.chatRoom.service.ChatRoomService;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.service.ItemService;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.web.chat.dto.ChatDto;
import com.deu.marketplace.web.chat.dto.ChatLogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {
//    - 채팅방 목록 조회 : GET - /api/v1/chat
//- 채팅방 생성 : POST - /api/v1/chat/create
//- 채팅방 입장 : GET - /api/v1/chat/{roomId} - 본인 토큰으로 인증
//- 채팅 전송 : POST - /api/v1/chat/{roomId}
//- → 웹 소켓으로 하는거 다시

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatLogService chatLogService;
    private final ChatRoomService chatRoomService;
    private final MemberService memberService;

    // /app/chat/enter 요청
    // /queue/chat/room/{roomId} 입력
    @MessageMapping("/chat/enter")
    public void enter(ChatDto chatDto) {
        chatDto.setMessage(chatDto.getSenderId() + "님 입장.");
        simpMessagingTemplate.convertAndSend("/queue/chat/" + chatDto.getRoomId(), chatDto);
    }

    @MessageMapping("/chat/message")
    public void chat(ChatDto chatDto) {
        simpMessagingTemplate.convertAndSend("/queue/chat/" + chatDto.getRoomId(), chatDto);
        ChatRoom chatRoom = chatRoomService.getOneChatRoomByRoomId(chatDto.getRoomId()).orElseThrow();
        ChatLog chatLog = ChatLog.bySenderId()
                .chatRoom(chatRoom)
                .content(chatDto.getMessage())
                .senderId(chatDto.getSenderId())
                .build();
        chatLogService.saveChatLog(chatLog);
        log.info("ChatLog is saved.");
    }

//      //이거 써야함
//    @GetMapping("/api/v1/chat/{chatRoomId}")
//    public ResponseEntity<?> getChatLogs(@PathVariable("chatRoomId") Long chatRoomId,
//                                         @PageableDefault(size = 30, page = 0,
//                                                 sort = "lastModifiedDate",
//                                                 direction = Sort.Direction.DESC) Pageable pageable) {
//        log.info("Get ChatLogs Page");
//        Page<ChatLog> chatLogPage = chatLogService.getChatLogPage(chatRoomId, pageable);
//        Page<ChatLogDto> chatLogDtos = chatLogPage.map(chatLog -> {
//            return ChatLogDto.builder()
//                    .chatLogId(chatLog.getId())
//                    .senderId(chatLog.getSender().getId())
//                    .recipientId(chatLog.getRecipient().getId())
//                    .message(chatLog.getContent())
//                    .lastModifiedDate(chatLog.getLastModifiedDate())
//                    .isRead(chatLog.isRead())
//                    .build();
//        });
//
//        return ResponseEntity.ok().body(chatLogDtos);
//    }

    @GetMapping("/api/v1/chat/{chatRoomId}")
    public ResponseEntity<?> getChatLogs(@PathVariable("chatRoomId") Long chatRoomId,
                                         @PageableDefault(size = 30, page = 0,
                                                 sort = "lastModifiedDate",
                                                 direction = Sort.Direction.DESC) Pageable pageable) {
        List<ChatLog> chatLogPage = chatLogService.getChatLogPage(chatRoomId, pageable).getContent();
        List<ChatLogDto> chatLogDtos = new ArrayList<>();
        for (ChatLog chatLog : chatLogPage) {
            chatLogDtos.add(ChatLogDto.builder()
                    .chatLogId(chatLog.getId())
                    .senderId(chatLog.getSender().getId())
                    .recipientId(chatLog.getRecipient().getId())
                    .message(chatLog.getContent())
                    .lastModifiedDate(chatLog.getLastModifiedDate())
                    .read(chatLog.isRead())
                    .build());
        }

        return ResponseEntity.ok().body(chatLogDtos);
    }

}
