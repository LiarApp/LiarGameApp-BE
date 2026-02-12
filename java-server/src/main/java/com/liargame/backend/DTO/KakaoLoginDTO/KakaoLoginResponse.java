package com.liargame.backend.DTO.KakaoLoginDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoLoginResponse {
    private Long id;
    private boolean isExist;
}
