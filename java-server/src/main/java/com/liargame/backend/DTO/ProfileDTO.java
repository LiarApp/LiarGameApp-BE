package com.liargame.backend.DTO;

import com.liargame.backend.Entity.ProfileImg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProfileDTO {
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class Response {
        private Long id;
        private String nickname;
        private ProfileImg profileImg;
    }

    @Getter @Setter
    public static class UpdateRequest {
        private String nickname;
        private ProfileImg profileImg;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class UpdateResponse {
        private boolean isUpdated;
        private String message;
        private Long id;
        private String nickname;
        private ProfileImg profileImg;
    }
}