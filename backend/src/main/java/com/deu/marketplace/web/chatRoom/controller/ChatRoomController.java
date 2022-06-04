package com.deu.marketplace.web.chatRoom.controller;

import com.deu.marketplace.common.ApiResponse;
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
import com.deu.marketplace.s3.S3Uploader;
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
import java.util.Optional;

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
    private final S3Uploader s3Uploader;
//    private final ChatLogService chatLogService;

    @PostMapping("/new")
    public ApiResponse createChatRoom(@AuthenticationPrincipal Long memberId,
                                      @RequestBody CreateChatRoomDto createChatRoomDto) throws URISyntaxException {
        log.info("Create ChatRoom.");
        Member requestedMember = memberService.getMemberById(memberId).orElseThrow();
        Item targetItem = itemService.getOneItemById(createChatRoomDto.getItemId()).orElseThrow();

        Optional<ChatRoom> findChatRoom =
                chatRoomService.getOneChatRoomByItemIdAndMemberId(
                                targetItem.getId(),
                                requestedMember.getId()
                );

        if (findChatRoom.isPresent()) {
            return ApiResponse.success("result", findChatRoom.orElseThrow().getId());
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .requestedMember(requestedMember)
                .item(targetItem)
                .build();
        ChatRoom createdChatRoom = chatRoomService.createChatRoom(chatRoom);

        chatLogService.saveChatLog(chatRoom, chatRoom.getRequestedMember(),
                chatRoom.getItem().getMember(), createChatRoomDto.getMessage());

        return ApiResponse.success("result", createdChatRoom.getId());
    }

    @GetMapping
    public ApiResponse getChatRooms(@PageableDefault(size = 20, page = 0) Pageable pageable,
                                          @AuthenticationPrincipal Long memberId) {
        log.info("Get ChatRooms. Page : " + pageable.getPageNumber());
        Page<ChatRoomViewDto> chatRoomPages =
                chatRoomViewRepository.getChatRoomPages(memberId, pageable);
        Page<ChatRoomListDto> chatRoomListDtos = chatRoomPages.map(chatRoomViewDto -> {
            return ChatRoomListDto.builder()
                    .chatRoomId(chatRoomViewDto.getChatRoomId())
                    .itemInfo(chatRoomViewDto.getItemInfo())
                    .targetMemberInfo(getTargetMemberInfo(memberId,
                            chatRoomViewDto.getSavedItemMemberInfo(),
                            chatRoomViewDto.getRequestedMemberInfo()))
                    .lastLogInfo(chatRoomViewDto.getLastLogInfo())
                    .dealInfo(chatRoomViewDto.getDealInfo())
                    .build();
        });
        Map<Long, Long> notReadCounts =
                chatRoomViewRepository.getNotReadCounts(chatRoomPages.getContent(), memberId);
        for (ChatRoomViewDto chatRoomViewDto : chatRoomPages) {
            chatRoomViewDto.getLastLogInfo().setNotReadNum(notReadCounts.get(chatRoomViewDto.getChatRoomId()));
            if (chatRoomViewDto.getItemInfo().getItemImg() != null) {
                chatRoomViewDto.imgToImgUrl(s3Uploader.toUrl(chatRoomViewDto.getItemInfo().getItemImg()));
            }
        }

        return ApiResponse.success("result", chatRoomListDtos);
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
    public ApiResponse enterChatRoom(@PathVariable("chatRoomId") Long chatRoomId,
                                     @AuthenticationPrincipal Long memberId) {
        log.info("Enter ChatRoom. Member : " + memberId + ", Room : " + chatRoomId);
        ChatRoomInfoDto chatRoomInfoDto =
                chatRoomViewRepository.getChatRoomInfo(chatRoomId, memberId).orElseThrow();
        chatRoomInfoDto.setMyId(memberId);
        if (chatRoomInfoDto.getItemInfo().getItemImg() != null) {
            chatRoomInfoDto.getItemInfo().imgToImgUrl(s3Uploader.toUrl(chatRoomInfoDto.getItemInfo().getItemImg()));
        }
        LocalDateTime now = LocalDateTime.now();

        Pageable pageable = PageRequest.of(0, 20, Sort.by("lastModifiedDate").descending());
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
        return ApiResponse.success("result", enterChatRoomDto);
    }

    // 채팅방 삭제

    // 채팅방 수정?? 필요한가?





    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateChatRoomDto {
        private Long itemId;
        private String message;
    }
}
