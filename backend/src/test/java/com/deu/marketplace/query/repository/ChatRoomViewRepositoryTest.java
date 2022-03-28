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
        Map<Long, Long> notReadCounts = chatRoomViewRepository.getNotReadCounts(result.getContent(), memberId);

        // then
        System.out.println("---------------------------------");
        for (ChatRoomViewDto chatRoomViewDto : result.getContent()) {
            chatRoomViewDto.getLastLogInfo()
                    .setNotReadNum(notReadCounts.get(chatRoomViewDto.getChatRoomId()));
        }

        for (ChatRoomViewDto chatRoomViewDto : result) {
            System.out.println("-------------------------------------------");
            System.out.println("responseDto.getChatRoomId() = " + chatRoomViewDto.getChatRoomId());
            System.out.println("responseDto.getItemInfo().getItemId() = " + chatRoomViewDto.getItemInfo().getItemId());
            System.out.println("responseDto.getItemInfo().getItemImg() = " + chatRoomViewDto.getItemInfo().getItemImg());
            System.out.println("responseDto.getItemInfo().getTitle() = " + chatRoomViewDto.getItemInfo().getTitle());
            System.out.println("responseDto.getItemInfo().getDealState() = " + chatRoomViewDto.getItemInfo().getDealState());
            System.out.println("responseDto.getSavedItemMemberInfo().getMemberId() = " + chatRoomViewDto.getSavedItemMemberInfo().getMemberId());
            System.out.println("responseDto.getSavedItemMemberInfo().getNickname() = " + chatRoomViewDto.getSavedItemMemberInfo().getNickname());
            System.out.println("responseDto.getRequestedMemberInfo().getMemberId() = " + chatRoomViewDto.getRequestedMemberInfo().getMemberId());
            System.out.println("responseDto.getRequestedMemberInfo().getNickname() = " + chatRoomViewDto.getRequestedMemberInfo().getNickname());
            System.out.println("responseDto.getLastLogInfo().getContent() = " + chatRoomViewDto.getLastLogInfo().getContent());
            System.out.println("responseDto.getLastLogInfo().getLastModifiedTime() = " + chatRoomViewDto.getLastLogInfo().getLastModifiedTime());
            System.out.println("responseDto.getLastLogInfo().getNotReadNum() = " + chatRoomViewDto.getLastLogInfo().getNotReadNum());
        }


    }
}