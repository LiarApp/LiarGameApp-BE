package com.liargame.backend.DTO.ReportDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportRequest {
    private Long userId;
    private Long reportedUserId;
    private String reason;
}
