package com.liargame.backend.DTO.ProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {
    private Long id;
    private boolean isUpdated;
}
