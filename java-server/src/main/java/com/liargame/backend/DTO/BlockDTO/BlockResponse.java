package com.liargame.backend.DTO.BlockDTO;

import com.liargame.backend.Entity.ProfileImg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlockResponse {
    private Long blockedUserId;
    private String nickname;
    private ProfileImg profileImg;
}
