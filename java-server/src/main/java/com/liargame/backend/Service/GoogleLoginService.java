package com.liargame.backend.Service;

import com.liargame.backend.DTO.LoginDTO;
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
                "&scope=openid%20profile%20email" +
                "&state=GOOGLE";
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

        ResponseEntity<LoginDTO.GoogleTokenResponse> response = restTemplate.postForEntity(
                tokenUrl,
                request,
                LoginDTO.GoogleTokenResponse.class
        );
        return response.getBody().getAccessToken();
    }

    private String getUserInfo(String accessToken) {
        String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<LoginDTO.GoogleUserInfoResponse> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                request,
                LoginDTO.GoogleUserInfoResponse.class
        );

        return response.getBody().getId();
    }

    @Transactional
    public LoginDTO.Response googleLogin(String code, LoginPath loginPath) {
        String accessToken = getAccessToken(code);
        String googleId = getUserInfo(accessToken);

        Optional<SocialAccount> existingAccount = socialAccountRepository
                .findByLoginPathAndProviderId(loginPath, googleId);

        if (existingAccount.isPresent()) {
            Long userId = existingAccount.get().getUser().getId();
            return new LoginDTO.Response(userId, true);  // [수정] user id, 회원 가입 여부를 반환하도록 수정
        } else {
            User newUser = new User();
            newUser.setProfileImg(ProfileImg.IMAGE1);  // [추가] default 프로필 이미지 설정
            userRepository.save(newUser);

            SocialAccount newAccount = new SocialAccount();
            newAccount.setLoginPath(loginPath);
            newAccount.setProviderId(googleId);
            newAccount.setUser(newUser);
            socialAccountRepository.save(newAccount);

            return new LoginDTO.Response(newUser.getId(), false);
        }
    }
}