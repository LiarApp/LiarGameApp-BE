package com.liargame.backend.Entity;

import lombok.Getter;

@Getter
public enum FriendStatus {
    // 친구 요청을 보낸 상태
    PENDING,
    // 친구 요청을 수락한 상태
    ACCEPTED
}