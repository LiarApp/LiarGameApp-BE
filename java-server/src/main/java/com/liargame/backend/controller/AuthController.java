package com.liargame.backend.controller;

import com.liargame.backend.entity.User;
import com.liargame.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    @GetMapping("/check-nickname")
    public ResponseEntity<?> checkNickname(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        User user = userRepository.findByEmail(email).orElseThrow();

        // 닉네임이 있는지 확인 -> 입력 필요 여부 설정
        if (user.getNickname() == null) {
            return ResponseEntity.ok(Map.of(
                    "needsNickname", true,
                    "email", email
            ));
        } else {
            return ResponseEntity.ok(Map.of(
                    "needsNickname", false,
                    "email", email
            ));
        }
    }

    @PostMapping("/set-nickname")
    public ResponseEntity<?> setNickname(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestBody Map<String, String> request) {

        String nickname = request.get("nickname");
        String email = principal.getAttribute("email");

        // nickname 중복 체크
        if (userRepository.existsByNickname(nickname)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "이미 사용 중인 닉네임입니다."
            ));
        }

        // nickname 설정
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setNickname(nickname);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "user", user
        ));
    }

    // nickname 중복 체크 API
    @GetMapping("/check-nickname-duplicate")
    public ResponseEntity<?> checkNicknameDuplicate(@RequestParam String nickname) {
        boolean exists = userRepository.existsByNickname(nickname);
        return ResponseEntity.ok(Map.of("exists", exists));
    }
}
