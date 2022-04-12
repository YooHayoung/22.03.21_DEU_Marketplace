package com.deu.marketplace.web.chat.controller;//package com.deu.marketplace.web.chat.controller;

import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import com.deu.marketplace.domain.chatLog.service.ChatLogService;
import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.chatRoom.service.ChatRoomService;
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
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    // /pub/enter/{roomId} 요청
    // /sub/chat/{roomId}
    @MessageMapping("/enter/{roomId}")
    @SendTo("/sub/chat/{roomId}")
    public String enter(@DestinationVariable("roomId") Long roomId,
                      @AuthenticationPrincipal Long memberId) {
        log.info("enter room : " + roomId + ", member : " + memberId);
        ChatRoom chatRoom =
                chatRoomService.getOneToEnterChatRoom(roomId, memberId).orElseThrow();

//        chatDto.setMessage(memberId + "님 입장.");
//        simpMessagingTemplate.convertAndSend("/queue/chat/" + roomId,
//                memberId + "님 입장.");
        return "System : " + memberId + "님 입장";
    }

    // /pub/chat/{roomId}
    @MessageMapping("/chat/{roomId}")
    public void chat(@DestinationVariable("roomId") Long roomId,
                     @Payload ChatDto chatDto) {
        log.info("send chat");
//        log.info("memberId :" + memberId.toString());
        log.info("roomId : " + roomId);
        log.info("message : " + chatDto.getMessage() + ", senderId : " + chatDto.getSenderId());
        ChatRoom chatRoom = chatRoomService.getOneChatRoomByRoomId(roomId).orElseThrow();
        log.info(chatRoom.getRequestedMember().getId().toString());
        ChatLogDto chatLogDto;
        if (chatDto.getSenderId() == chatRoom.getItem().getMember().getId()) {
            chatLogDto = ChatLogDto.byEntity()
                    .chatLog(chatLogService.saveChatLog(chatRoom, chatRoom.getItem().getMember(),
                    chatRoom.getRequestedMember(), chatDto.getMessage()))
                    .build();
        } else {
            chatLogDto = ChatLogDto.byEntity()
                    .chatLog(chatLogService.saveChatLog(chatRoom, chatRoom.getRequestedMember(),
                    chatRoom.getItem().getMember(), chatDto.getMessage()))
                    .build();
        }
        simpMessagingTemplate.convertAndSend("/sub/chat/" + roomId, chatLogDto);

        log.info("ChatLog is saved.");
    }

      //이거 써야함
    @GetMapping("/api/v1/chat/{chatRoomId}")
    public ApiResponse getChatLogs(@PathVariable("chatRoomId") Long chatRoomId,
                                   @RequestParam("enterTime") String enterTime,
                                   @PageableDefault(size = 30, page = 1,
                                                 sort = "lastModifiedDate",
                                                 direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("Get ChatLogs Page");
        log.info(enterTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(enterTime.replace("_", " "), formatter);
        Page<ChatLog> chatLogPage = chatLogService.getChatLogPage(chatRoomId, dateTime, pageable);
        Page<ChatLogDto> chatLogDtos = chatLogPage.map(chatLog -> {
            return ChatLogDto.builder()
                    .chatLogId(chatLog.getId())
                    .senderId(chatLog.getSender().getId())
                    .recipientId(chatLog.getRecipient().getId())
                    .message(chatLog.getContent())
                    .lastModifiedDate(chatLog.getLastModifiedDate())
                    .read(chatLog.isRead())
                    .build();
        });

        return ApiResponse.success("result", chatLogDtos);
    }

}
