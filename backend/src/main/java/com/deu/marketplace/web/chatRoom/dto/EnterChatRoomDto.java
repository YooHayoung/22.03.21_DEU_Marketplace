package com.deu.marketplace.web.chatRoom.dto;

import com.deu.marketplace.query.chatRoom.dto.ChatRoomInfoDto;
import com.deu.marketplace.web.chat.dto.ChatLogDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterChatRoomDto {
    private ChatRoomInfoDto chatRoomInfo;
    private Page<ChatLogDto> chatLogInfos;

    @Builder
    public EnterChatRoomDto(ChatRoomInfoDto chatRoomInfoDto, Page<ChatLogDto> chatLogDtoPage) {
        this.chatRoomInfo = chatRoomInfoDto;
        this.chatLogInfos = chatLogDtoPage;
    }
}
