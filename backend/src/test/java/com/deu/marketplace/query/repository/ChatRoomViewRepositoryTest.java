package com.deu.marketplace.query.repository;

import com.deu.marketplace.query.dto.ChatRoomViewDto;
import com.deu.marketplace.web.chatRoom.dto.ChatRoomListResponseDto;
import com.deu.marketplace.web.chatRoom.dto.ItemInfo;
import com.deu.marketplace.web.chatRoom.dto.LogInfo;
import com.deu.marketplace.web.chatRoom.dto.MemberInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatRoomViewRepositoryTest {

    @Autowired
    ChatRoomViewRepository chatRoomViewRepository;

    @Test
    public void test() throws Exception {
        // given
        Pageable pageable = Pageable.ofSize(100).withPage(0);
        Long memberId = 1L;

        // when
        Page<ChatRoomViewDto> result = chatRoomViewRepository.getChatRoomPages(memberId, pageable);
        List<ChatRoomViewDto> content = result.getContent();
        System.out.println("result = " + result.getTotalElements());
        Map<Long, Long> notReadCounts = chatRoomViewRepository.getNotReadCounts(content, memberId);
        List<ChatRoomListResponseDto> responseDtos = new ArrayList<>();
        // then
        System.out.println("---------------------------------");
        for (ChatRoomViewDto chatRoomViewDto : content) {
            System.out.println("chatRoomViewDto.getChatRoomId() = " + chatRoomViewDto.getChatRoomId());
            ItemInfo itemInfo = ItemInfo.builder()
                    .itemId(chatRoomViewDto.getItemId())
                    .itemImg(chatRoomViewDto.getItemImg())
                    .title(chatRoomViewDto.getTitle())
                    .dealState(chatRoomViewDto.getDealState())
                    .build();
            MemberInfo itemSavedMemberInfo = MemberInfo.builder()
                    .memberId(chatRoomViewDto.getItemSavedMemberId())
                    .nickname(chatRoomViewDto.getItemSavedMemberNickname())
                    .build();
            MemberInfo requestedMemberInfo = MemberInfo.builder()
                    .memberId(chatRoomViewDto.getRequestedMemberId())
                    .nickname(chatRoomViewDto.getRequestedMemberNickname())
                    .build();
            LogInfo logInfo = LogInfo.builder()
                    .content(chatRoomViewDto.getLastLogContent())
                    .lastModifiedTime(chatRoomViewDto.getLastModifiedTime())
                    .notReadNum(notReadCounts.get(chatRoomViewDto.getChatRoomId()))
                    .build();
            ChatRoomListResponseDto responseDto = ChatRoomListResponseDto.builder()
                    .chatRoomId(chatRoomViewDto.getChatRoomId())
                    .itemInfo(itemInfo)
                    .savedItemMemberInfo(itemSavedMemberInfo)
                    .requestedMemberInfo(requestedMemberInfo)
                    .lastLogInfo(logInfo)
                    .build();
            responseDtos.add(responseDto);
        }

        for (ChatRoomListResponseDto responseDto : responseDtos) {
            System.out.println("-------------------------------------------");
            System.out.println("responseDto.getChatRoomId() = " + responseDto.getChatRoomId());
            System.out.println("responseDto.getItemInfo().getItemId() = " + responseDto.getItemInfo().getItemId());
            System.out.println("responseDto.getItemInfo().getItemImg() = " + responseDto.getItemInfo().getItemImg());
            System.out.println("responseDto.getItemInfo().getTitle() = " + responseDto.getItemInfo().getTitle());
            System.out.println("responseDto.getItemInfo().getDealState() = " + responseDto.getItemInfo().getDealState());
            System.out.println("responseDto.getSavedItemMemberInfo().getMemberId() = " + responseDto.getSavedItemMemberInfo().getMemberId());
            System.out.println("responseDto.getSavedItemMemberInfo().getNickname() = " + responseDto.getSavedItemMemberInfo().getNickname());
            System.out.println("responseDto.getRequestedMemberInfo().getMemberId() = " + responseDto.getRequestedMemberInfo().getMemberId());
            System.out.println("responseDto.getRequestedMemberInfo().getNickname() = " + responseDto.getRequestedMemberInfo().getNickname());
            System.out.println("responseDto.getLastLogInfo().getContent() = " + responseDto.getLastLogInfo().getContent());
            System.out.println("responseDto.getLastLogInfo().getLastModifiedTime() = " + responseDto.getLastLogInfo().getLastModifiedTime());
            System.out.println("responseDto.getLastLogInfo().getNotReadNum() = " + responseDto.getLastLogInfo().getNotReadNum());
        }


    }
}