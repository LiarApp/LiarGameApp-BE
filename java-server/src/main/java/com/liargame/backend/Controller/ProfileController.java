package com.liargame.backend.Controller;

import com.liargame.backend.DTO.ProfileDTO.ProfileRequestDTO;
import com.liargame.backend.DTO.ProfileDTO.ProfileResponseDTO;
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

    @PostMapping("/update")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@RequestBody ProfileRequestDTO dto) {
        // 사용자 프로필을 업데이트합니다.
        ProfileResponseDTO response = profileService.updateProfile(
            dto.getId(),
            dto.getNickname(),
            dto.getProfileImg()
        );

        return ResponseEntity.ok(response);
    }
}
