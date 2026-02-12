package com.liargame.backend.DTO.ProfileDTO;

import com.liargame.backend.Entity.ProfileImg;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileRequestDTO {
    private Long id;
    private String nickname;
    private ProfileImg profileImg;
}
