package com.deu.marketplace.web.oauth.controller;

import com.deu.marketplace.config.security.service.CustomOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final CustomOAuthService oAuthService;

//    @GetMapping("/login/oauth/{provider}")
//    public ResponseEntity<?> login(@PathVariable("provider") String provider,
//                                   @RequestParam String code) {
//
//    }
}
