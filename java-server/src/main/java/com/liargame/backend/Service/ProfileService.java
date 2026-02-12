package com.liargame.backend.Service;

import com.liargame.backend.DTO.ProfileDTO.ProfileResponseDTO;
import com.liargame.backend.Entity.ProfileImg;
import com.liargame.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;

    @Transactional
    public ProfileResponseDTO updateProfile(Long id, String nickname, ProfileImg profileImg) {
        // 사용자 프로필을 업데이트합니다.
        int isUpdated = userRepository.updateUserInfo(id, nickname, profileImg);
        return new ProfileResponseDTO(id, isUpdated==1);
    }
}
