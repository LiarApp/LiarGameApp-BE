package com.liargame.backend.Service;

import com.liargame.backend.DTO.KakaoLoginDTO.KakaoTokenResponse;
import com.liargame.backend.DTO.KakaoLoginDTO.KakaoUserInfoResponse;
import com.liargame.backend.Entity.SocialAccount;
import com.liargame.backend.Entity.User;
import com.liargame.backend.Repository.SocialAccountRepository;
import com.liargame.backend.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;
    private final SocialAccountRepository socialAccountRepository;

    // 카카오 URL을 반환합니다.
    public String getKakaoLoginUrl() {
        return UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }

    // 카카오 로그인을 진행합니다.
    @Transactional
    public void kakaoLogin(String code) {
        // 1. Access Token 발급
        String accessToken = getAccessToken(code);

        // 2. 사용자 고유 ID 가져오기
        String providerId = getUserInfo(accessToken);

        // 3. 기존 소셜 계정 존재 여부 확인
        Optional<SocialAccount> existingAccount = socialAccountRepository.findByProviderId(providerId);

        // 4. 신규 가입
        if (existingAccount.isEmpty()) {
            User newUser = new User();
            userRepository.save(newUser);

            SocialAccount newSocialAccount = new SocialAccount();
            newSocialAccount.setProviderId(providerId);
            newSocialAccount.setProviderName("KAKAO");

            newSocialAccount.setUser(newUser);
            newUser.getSocialAccounts().add(newSocialAccount);
            socialAccountRepository.save(newSocialAccount);
        }
    }

    // Access Token을 발급합니다.
    private String getAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        // 1. Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 2. Body 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", clientSecret);

        // 3. 요청 객체 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // 4. POST 요청 및 응답 받기
        KakaoTokenResponse response = restTemplate.postForObject(tokenUrl, request, KakaoTokenResponse.class);

        return response.getAccessToken();
    }

    // 사용자의 고유 ID를 반환합니다.
    private String getUserInfo(String accessToken) {
        String infoUrl = "https://kapi.kakao.com/v2/user/me";

        // 1. Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        // 2. 카카오에 사용자 정보 요청
        ResponseEntity<KakaoUserInfoResponse> response = restTemplate.exchange(
                infoUrl,
                HttpMethod.GET,
                request,
                KakaoUserInfoResponse.class
        );

        // 3. 고유 ID 반환
        return String.valueOf(response.getBody().getId());
    }
}
