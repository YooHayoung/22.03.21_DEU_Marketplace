package com.deu.marketplace.web.chatRoom.controller;

import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.chatRoom.service.ChatRoomService;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.service.ItemService;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.query.dto.ChatRoomInfoDto;
import com.deu.marketplace.query.dto.ChatRoomViewDto;
import com.deu.marketplace.query.repository.ChatRoomViewRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatRoom")
public class ChatRoomController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final ChatRoomService chatRoomService;
    private final ChatRoomViewRepository chatRoomViewRepository;
//    private final ChatLogService chatLogService;

    @PostMapping("/new")
    public ResponseEntity<?> createChatRoom(@RequestHeader("memberId") Long memberId,
                                            @RequestBody ItemIdDto itemIdDto) throws URISyntaxException {
        log.info("Create ChatRoom.");
        Member requestedMember = memberService.getMemberById(memberId).orElseThrow();
        Item targetItem = itemService.getOneItemById(itemIdDto.getItemId()).orElseThrow();
        ChatRoom chatRoom = ChatRoom.builder()
                .requestedMember(requestedMember)
                .item(targetItem)
                .build();
        ChatRoom createdChatRoom = chatRoomService.createChatRoom(chatRoom);

        URI redirectUri = new URI("http://localhost:8080/api/v1/chatRoom/" + createdChatRoom.getId());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

//    // 이거 써야함
//    @GetMapping
//    public ResponseEntity<?> getChatRooms(@PageableDefault(size = 20, page = 0) Pageable pageable,
//                                          @RequestHeader(value = "memberId") Long memberId) {
//        log.info("Get ChatRooms. Page : " + pageable.getPageNumber());
//        Page<ChatRoomViewDto> chatRoomPages =
//                chatRoomViewRepository.getChatRoomPages(memberId, pageable);
//        Map<Long, Long> notReadCounts = chatRoomViewRepository.getNotReadCounts(chatRoomPages.getContent(), memberId);
//        for (ChatRoomViewDto chatRoomViewDto : chatRoomPages) {
//            chatRoomViewDto.getLastLogInfo().setNotReadNum(notReadCounts.get(chatRoomViewDto.getChatRoomId()));
//        }
//
//        return ResponseEntity.ok().body(chatRoomPages);
//    }
    @GetMapping
    public ResponseEntity<?> getChatRooms(@PageableDefault(size = 20, page = 0) Pageable pageable,
                                          @RequestHeader(value = "memberId") Long memberId) {
        log.info("Get ChatRooms. Page : " + pageable.getPageNumber());
        List<ChatRoomViewDto> chatRoomPages =
                chatRoomViewRepository.getChatRoomPages(memberId, pageable).getContent();
        Map<Long, Long> notReadCounts = chatRoomViewRepository.getNotReadCounts(chatRoomPages, memberId);
        for (ChatRoomViewDto chatRoomViewDto : chatRoomPages) {
            chatRoomViewDto.getLastLogInfo().setNotReadNum(notReadCounts.get(chatRoomViewDto.getChatRoomId()));
        }

        return ResponseEntity.ok().body(chatRoomPages);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<?> enterChatRoom(@PathVariable("chatRoomId") Long chatRoomId,
                                           @RequestHeader("memberId") Long memberId) throws URISyntaxException {
        log.info("Enter ChatRoom. Member : " + memberId + ", Room : " + chatRoomId);
        ChatRoomInfoDto chatRoomInfoDto =
                chatRoomViewRepository.getChatRoomInfo(chatRoomId, memberId).orElseThrow();
        chatRoomInfoDto.setMyId(memberId);

//        URI redirectUri = new URI("http://localhost:8080/api/v1/chat/" + chatRoomInfoDto.getChatRoomId());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(redirectUri);
//        return new ResponseEntity<>(chatRoomInfoDto, httpHeaders, HttpStatus.SEE_OTHER);
        return new ResponseEntity<>(chatRoomInfoDto, null, HttpStatus.OK);
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
