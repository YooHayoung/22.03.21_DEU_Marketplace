package com.deu.marketplace.web.chatRoom.controller;

import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import com.deu.marketplace.domain.chatLog.service.ChatLogService;
import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.chatRoom.service.ChatRoomService;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.service.ItemService;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.query.chatRoom.dto.ChatRoomInfoDto;
import com.deu.marketplace.query.chatRoom.dto.ChatRoomViewDto;
import com.deu.marketplace.query.chatRoom.repository.ChatRoomViewRepository;
import com.deu.marketplace.web.chat.dto.ChatLogDto;
import com.deu.marketplace.web.chatRoom.dto.ChatRoomListDto;
import com.deu.marketplace.web.chatRoom.dto.EnterChatRoomDto;
import com.deu.marketplace.web.chatRoom.dto.MemberInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatRoom")
public class ChatRoomController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final ChatRoomService chatRoomService;
    private final ChatLogService chatLogService;
    private final ChatRoomViewRepository chatRoomViewRepository;
//    private final ChatLogService chatLogService;

    @PostMapping("/new")
    public ResponseEntity<?> createChatRoom(@AuthenticationPrincipal Long memberId,
                                            @RequestBody ItemIdDto itemIdDto) throws URISyntaxException {
        log.info("Create ChatRoom.");
        Member requestedMember = memberService.getMemberById(memberId).orElseThrow();
        Item targetItem = itemService.getOneItemById(itemIdDto.getItemId()).orElseThrow();
        ChatRoom chatRoom = ChatRoom.builder()
                .requestedMember(requestedMember)
                .item(targetItem)
                .build();
        ChatRoom createdChatRoom = chatRoomService.createChatRoom(chatRoom);

        URI redirectUri = new URI("http://localhost:8000/api/v1/chatRoom/" + createdChatRoom.getId());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @GetMapping
    public ResponseEntity<?> getChatRooms(@PageableDefault(size = 20, page = 0) Pageable pageable,
                                          @AuthenticationPrincipal Long memberId) {
        log.info("Get ChatRooms. Page : " + pageable.getPageNumber());
        Page<ChatRoomViewDto> chatRoomPages =
                chatRoomViewRepository.getChatRoomPages(memberId, pageable);
        Page<ChatRoomListDto> ChatRoomListDtos = chatRoomPages.map(chatRoomViewDto -> {
            return ChatRoomListDto.builder()
                    .chatRoomId(chatRoomViewDto.getChatRoomId())
                    .itemInfo(chatRoomViewDto.getItemInfo())
                    .targetMemberInfo(getTargetMemberInfo(memberId,
                            chatRoomViewDto.getSavedItemMemberInfo(),
                            chatRoomViewDto.getRequestedMemberInfo()))
                    .lastLogInfo(chatRoomViewDto.getLastLogInfo())
                    .build();
        });
        Map<Long, Long> notReadCounts = chatRoomViewRepository.getNotReadCounts(chatRoomPages.getContent(), memberId);
        for (ChatRoomViewDto chatRoomViewDto : chatRoomPages) {
            chatRoomViewDto.getLastLogInfo().setNotReadNum(notReadCounts.get(chatRoomViewDto.getChatRoomId()));
        }

        return ResponseEntity.ok().body(ChatRoomListDtos);
    }

    private MemberInfo getTargetMemberInfo(Long myId, MemberInfo itemSavedMemberInfo,
                                           MemberInfo requestedMemberInfo) {
        if (itemSavedMemberInfo.getMemberId() != myId) {
            return itemSavedMemberInfo;
        } else {
            return requestedMemberInfo;
        }
    }

//    @GetMapping
//    public ResponseEntity<?> getChatRooms(@PageableDefault(size = 20, page = 0) Pageable pageable,
//                                          @RequestHeader(value = "memberId") Long memberId) {
//        log.info("Get ChatRooms. Page : " + pageable.getPageNumber());
//        List<ChatRoomViewDto> chatRoomPages =
//                chatRoomViewRepository.getChatRoomPages(memberId, pageable).getContent();
//        Map<Long, Long> notReadCounts = chatRoomViewRepository.getNotReadCounts(chatRoomPages, memberId);
//        for (ChatRoomViewDto chatRoomViewDto : chatRoomPages) {
//            chatRoomViewDto.getLastLogInfo().setNotReadNum(notReadCounts.get(chatRoomViewDto.getChatRoomId()));
//        }
//
//        return ResponseEntity.ok().body(chatRoomPages);
//    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<?> enterChatRoom(@PathVariable("chatRoomId") Long chatRoomId,
                                           @AuthenticationPrincipal Long memberId) throws URISyntaxException {
        log.info("Enter ChatRoom. Member : " + memberId + ", Room : " + chatRoomId);
        ChatRoomInfoDto chatRoomInfoDto =
                chatRoomViewRepository.getChatRoomInfo(chatRoomId, memberId).orElseThrow();
        chatRoomInfoDto.setMyId(memberId);
        LocalDateTime now = LocalDateTime.now();

//        URI redirectUri = new URI("http://localhost:8080/api/v1/chat/" + chatRoomInfoDto.getChatRoomId());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(redirectUri);
//        return new ResponseEntity<>(chatRoomInfoDto, httpHeaders, HttpStatus.SEE_OTHER);
        Pageable pageable = PageRequest.of(0, 2, Sort.by("lastModifiedDate").descending());
        chatLogService.bulkUpdateRead(chatRoomId, memberId, now);
        Page<ChatLog> chatLogPage = chatLogService.getChatLogPage(chatRoomId, now, pageable);
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
        EnterChatRoomDto enterChatRoomDto = EnterChatRoomDto.builder()
                .chatRoomInfoDto(chatRoomInfoDto)
                .chatLogDtoPage(chatLogDtos)
                .build();
        return ResponseEntity.ok().body(enterChatRoomDto);
    }

    // 채팅방 삭제

    // 채팅방 수정?? 필요한가?





    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemIdDto {
        private Long itemId;
    }
}
