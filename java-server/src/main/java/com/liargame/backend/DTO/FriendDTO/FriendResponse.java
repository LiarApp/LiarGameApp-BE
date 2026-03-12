package com.liargame.backend.DTO.FriendDTO;

import com.liargame.backend.Entity.ProfileImg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendResponse {
    private Long friendId;
    private String nickname;
    private ProfileImg profileImg;
}
