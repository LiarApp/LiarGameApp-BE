package com.liargame.backend.Controller;

import com.liargame.backend.DTO.LoginResponse;
import com.liargame.backend.Service.KakaoLoginService;
import com.liargame.backend.Service.GoogleLoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/login")
@Tag(name="Login API", description="로그인 기능")
@RequiredArgsConstructor
public class LoginController {

    private final KakaoLoginService kakaoLoginService;
    private final GoogleLoginService googleLoginService;

    @Operation(summary="카카오 로그인 url 제공", description="카카오 로그인 URL을 제공합니다.")
    @GetMapping("/kakao/url")
    public ResponseEntity<String> getKakaoLoginUrl() {
        return ResponseEntity.ok(kakaoLoginService.getKakaoLoginUrl());
    }

    @Operation(summary="카카오 로그인 시작", description="인가 코드를 이용해 로그인을 진행합니다.")
    @GetMapping("/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam("code") String code) {
        LoginResponse response = kakaoLoginService.kakaoLogin(code);
        return ResponseEntity.ok(response);
    }

    @Operation(summary="구글 로그인 url 제공", description="구글 로그인 URL을 제공합니다.")
    @GetMapping("/google/url")
    public ResponseEntity<String> getGoogleLoginUrl() {
        return ResponseEntity.ok(googleLoginService.getGoogleLoginUrl());
    }

    @Operation(summary="구글 로그인 시작", description="인가 코드를 이용해 로그인을 진행합니다.")
    @GetMapping("/google")
    public ResponseEntity<LoginResponse> googleLogin(@RequestParam("code") String code) {
        LoginResponse response = googleLoginService.googleLogin(code);
        return ResponseEntity.ok(response);
    }
}