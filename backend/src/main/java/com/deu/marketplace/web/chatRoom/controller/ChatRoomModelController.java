package com.deu.marketplace.web.chatRoom.controller;

import com.deu.marketplace.query.dto.ChatRoomInfoDto;
import com.deu.marketplace.query.dto.ChatRoomViewDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chatRooms")
public class ChatRoomModelController {

    @GetMapping
    public String getRooms(Model model) {
        String baseUrl = "http://localhost:8080/api/v1/chatRoom";

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .queryParam("page", 0)
                .build(false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        headers.add("memberId", "1");

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // api 호출 타임아웃
        factory.setReadTimeout(5000);   // api 읽기 타임아웃

        RestTemplate template = new RestTemplate(factory);

        ResponseEntity<List> response = template.exchange(uriComponents.toUriString(),
                HttpMethod.GET, new HttpEntity<>(headers), List.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("contents", response.getBody());
        }

        return "/ChatRoomList";
    }

    @GetMapping("/{chatRoomId}")
    public String enterChatRoom(@PathVariable("chatRoomId") Long chatRoomId, Model model) {
        String baseUrl = "http://localhost:8080/api/v1/chatRoom/" + chatRoomId;

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .build(false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        headers.add("memberId", "1");

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // api 호출 타임아웃
        factory.setReadTimeout(5000);   // api 읽기 타임아웃

        RestTemplate template = new RestTemplate(factory);

        ResponseEntity<ChatRoomInfoDto> response = template.exchange(uriComponents.toUriString(),
                HttpMethod.GET, new HttpEntity<>(headers), ChatRoomInfoDto.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("content", response.getBody());

            baseUrl = "http://localhost:8080/api/v1/chat/" + response.getBody().getChatRoomId();
            UriComponents build = UriComponentsBuilder.fromHttpUrl(baseUrl).build(false);
            ResponseEntity<List> responseEntity = template.exchange(build.toUriString(), HttpMethod.GET, new HttpEntity<>(headers), List.class);
            model.addAttribute("chatLogs", responseEntity.getBody());
        }

        return "/ChatRoom";
    }
}
