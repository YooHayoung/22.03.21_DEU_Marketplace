package com.deu.marketplace.web.oauth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    @GetMapping("/oauth")
    public ResponseEntity<?> login() {
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header("location", "http://localhost:8080/oauth/authorization/naver?redirect_uri=http://localhost:3000/oauth/redirect").body(null);
    }
}
