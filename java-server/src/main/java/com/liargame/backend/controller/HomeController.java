package com.liargame.backend.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "Google Login Test - Visit: /oauth2/authorization/google";
    }

    @GetMapping("/home")
    public Map<String, Object> home(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }
}
