package com.liargame.backend.Service;

import com.liargame.backend.DTO.GoogleLoginDTO.GoogleTokenResponse;
import com.liargame.backend.DTO.GoogleLoginDTO.GoogleUserInfoResponse;
import com.liargame.backend.DTO.LoginResponseDTO;
import com.liargame.backend.Entity.LoginPath;
import com.liargame.backend.Entity.ProfileImg;
import com.liargame.backend.Entity.SocialAccount;
import com.liargame.backend.Entity.User;
import com.liargame.backend.Repository.SocialAccountRepository;
import com.liargame.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {

    private final UserRepository userRepository;
    private final SocialAccountRepository socialAccountRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    public String getGoogleLoginUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth?" +
                "client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=openid%20profile%20email";
    }

    private String getAccessToken(String code) {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<GoogleTokenResponse> response = restTemplate.postForEntity(
                tokenUrl,
                request,
                GoogleTokenResponse.class
        );
        return response.getBody().getAccessToken();
    }

    private String getUserInfo(String accessToken) {
        String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<GoogleUserInfoResponse> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                request,
                GoogleUserInfoResponse.class
        );

        return response.getBody().getId();
    }

    @Transactional
    public LoginResponseDTO googleLogin(String code) {
        System.out.println("=== 구글 로그인 시작 ===");
        System.out.println("Code: " + code);

        String accessToken = getAccessToken(code);
        System.out.println("✅ Access Token 발급 성공: " + accessToken.substring(0, 20) + "...");

        String googleId = getUserInfo(accessToken);
        System.out.println("✅ Google ID: " + googleId);

        Optional<SocialAccount> existingAccount = socialAccountRepository
                .findByLoginPathAndProviderId(LoginPath.GOOGLE, googleId);
        System.out.println("기존 계정 존재: " + existingAccount.isPresent());

        if (existingAccount.isPresent()) {
            Long userId = existingAccount.get().getUser().getId();
            System.out.println("✅ 기존 회원 로그인, User ID: " + userId);
            return new LoginResponseDTO(userId, true);  // [수정] user id, 회원 가입 여부를 반환하도록 수정
        } else {
            User newUser = new User();
            newUser.setProfileImg(ProfileImg.IMAGE1);  // [추가] default 프로필 이미지 설정
            userRepository.save(newUser);
            System.out.println("✅ User 저장 완료, ID: " + newUser.getId());

            SocialAccount newAccount = new SocialAccount();
            newAccount.setLoginPath(LoginPath.GOOGLE);
            newAccount.setProviderId(googleId);
            newAccount.setUser(newUser);
            socialAccountRepository.save(newAccount);
            System.out.println("✅ SocialAccount 저장 완료");

            System.out.println("=== 구글 로그인 완료 ===");
            return new LoginResponseDTO(newUser.getId(), false);
        }
    }
}