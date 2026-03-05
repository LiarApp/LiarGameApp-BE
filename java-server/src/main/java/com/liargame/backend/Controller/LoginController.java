package com.liargame.backend.Controller;

import com.liargame.backend.DTO.LoginResponse;
import com.liargame.backend.Entity.LoginPath;
import com.liargame.backend.Service.CommonLoginService;
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
    private final CommonLoginService commonLoginService;

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
    public ResponseEntity<LoginResponse> googleLogin(
            @RequestParam("code") String code,
            @RequestParam("state") String state) {  // state도 받아서 loginPath에 넣기
        LoginPath loginPath = LoginPath.valueOf(state);
        LoginResponse response = googleLoginService.googleLogin(code, loginPath);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "일반 회원가입", description = "전화번호와 비밀번호로 회원가입합니다.")
    @PostMapping("/common/register")
    public ResponseEntity<LoginResponse> register(
            @RequestParam String phoneNumber,
            @RequestParam String password) {
        return ResponseEntity.ok(commonLoginService.register(phoneNumber, password));
    }

    @Operation(summary = "일반 로그인", description = "전화번호와 비밀번호로 로그인합니다.")
    @PostMapping("/common/login")
    public ResponseEntity<LoginResponse> commonLogin(
            @RequestParam String phoneNumber,
            @RequestParam String password) {
        return ResponseEntity.ok(commonLoginService.login(phoneNumber, password));
    }
}