package com.liargame.backend.DTO.BlockDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlockRequest {
    private Long userId;
    private Long blockedUserId;
}
