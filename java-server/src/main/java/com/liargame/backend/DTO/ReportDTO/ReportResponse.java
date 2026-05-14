package com.liargame.backend.DTO.ReportDTO;

import com.liargame.backend.Entity.ProfileImg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private Long reportedUserId;
    private String nickname;
    private ProfileImg profileImg;
    private String reason;
}