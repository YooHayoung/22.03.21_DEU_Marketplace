package com.deu.marketplace.web.chatRoom.controller;

import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.chatRoom.service.ChatRoomService;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.service.ItemService;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatRoom")
public class ChatRoomController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final  ChatRoomService chatRoomService;

    @PostMapping("/new")
    public ResponseEntity<?> createChatRoom(@RequestHeader("memberId") Long memberId,
                                            @RequestBody ItemIdDto itemIdDto) {
        Member requestedMember = memberService.getMemberById(memberId).orElseThrow();
        Item targetItem = itemService.getOneItemById(itemIdDto.getItemId()).orElseThrow();
        ChatRoom chatRoom = ChatRoom.builder()
                .requestedMember(requestedMember)
                .item(targetItem)
                .build();
        ChatRoom createdChatRoom = chatRoomService.createChatRoom(chatRoom);
        return ResponseEntity.ok().body(createdChatRoom.getId());
    }

    @GetMapping
    public ResponseEntity<?> getChatRooms(@RequestHeader("memberId") Long memberId) {
        return null;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemIdDto {
        private Long itemId;
    }
}
