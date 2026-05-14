package com.liargame.backend.DTO.FriendDTO;

import com.liargame.backend.Entity.ProfileImg;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendRequest {
    private Long userId;
    private Long friendId;
}
