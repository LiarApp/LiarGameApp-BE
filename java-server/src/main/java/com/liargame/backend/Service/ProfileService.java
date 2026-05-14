package com.liargame.backend.Service;

import com.liargame.backend.DTO.ProfileDTO;
import com.liargame.backend.Entity.ProfileImg;
import com.liargame.backend.Entity.User;
import com.liargame.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;

    public ProfileDTO.Response getProfile(Long id) {
        // 사용자 프로필을 조회합니다.
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        return new ProfileDTO.Response(id, user.getNickname(), user.getProfileImg());
    }

    @Transactional
    public ProfileDTO.UpdateResponse updateProfile(Long id, String nickname, ProfileImg profileImg) {
        // 사용자 프로필을 업데이트합니다.
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        boolean isUpdated = true;
        String message = "프로필을 업데이트했습니다.";

        if (nickname != null && !nickname.equals(user.getNickname())) {
            // 닉네임 중복 여부 검사
            if (userRepository.existsByNickname(nickname)) {
                isUpdated = false;
                message = "이미 존재하는 닉네임입니다.";
            }
            // 닉네임 업데이트
            else user.setNickname(nickname);
        }

        // 프로필 이미지 업데이트
        if (profileImg != null && !profileImg.equals(user.getProfileImg()))
            user.setProfileImg(profileImg);

        return new ProfileDTO.UpdateResponse(isUpdated, message, id, user.getNickname(), user.getProfileImg());
    }
}
