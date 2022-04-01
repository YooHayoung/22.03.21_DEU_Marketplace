package com.deu.marketplace.web.oauth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    @GetMapping("/api/v1/oauth/refresh")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok().body("refresh OK");
    }
}
