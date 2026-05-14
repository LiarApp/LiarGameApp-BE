package com.liargame.backend.Controller;

import com.liargame.backend.DTO.ProfileDTO;
import com.liargame.backend.Service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins={"*"})
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO.Response> getProfile(@PathVariable Long id) {
        // 사용자의 프로필을 조회합니다.
        ProfileDTO.Response response = profileService.getProfile(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProfileDTO.UpdateResponse> updateProfile(
        @PathVariable Long id,
        ProfileDTO.UpdateRequest dto
    ) {
        // 사용자 프로필을 업데이트합니다.
        ProfileDTO.UpdateResponse response = profileService.updateProfile(
            id,
            dto.getNickname(),
            dto.getProfileImg()
        );

        return ResponseEntity.ok(response);
    }
}
