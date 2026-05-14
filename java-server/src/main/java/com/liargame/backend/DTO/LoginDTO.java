package com.liargame.backend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LoginDTO {
    @Getter @NoArgsConstructor @AllArgsConstructor
    public static class Response {
        private Long id;
        private boolean isExist;
    }

    @Getter @NoArgsConstructor
    public static class KakaoTokenResponse {
        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("refresh_token")
        private String refreshToken;

        @JsonProperty("expires_in")
        private int expiresIn;

        @JsonProperty("refresh_token_expires_in")
        private int refreshTokenExpiresIn;
    }

    @Getter @NoArgsConstructor
    public static class KakaoUserInfoResponse {
        private Long id;
    }
}
