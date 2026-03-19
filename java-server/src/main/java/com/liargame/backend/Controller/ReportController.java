package com.liargame.backend.Controller;

import com.liargame.backend.DTO.ReportDTO.ReportRequest;
import com.liargame.backend.DTO.ReportDTO.ReportResponse;
import com.liargame.backend.Service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/")
    public ResponseEntity<Void> createdReport(@RequestBody ReportRequest request) {
        reportService.createReport(request.getUserId(), request.getReportedUserId(), request.getReason());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ReportResponse>> getAll() {
        List<ReportResponse> reportResponseList = reportService.getAll();
        return ResponseEntity.ok(reportResponseList);
    }

    @GetMapping("/me")
    public ResponseEntity<List<ReportResponse>> getReportedUsers(@RequestParam Long userId) {
        List<ReportResponse> reportResponseList = reportService.getReportedUsers(userId);
        return ResponseEntity.ok(reportResponseList);
    }

    @DeleteMapping("/{userId}/{reportedUserId}")
    public ResponseEntity<Void> cancelReport(@PathVariable Long userId, @PathVariable Long reportedUserId) {
        reportService.cancelReport(userId, reportedUserId);
        return ResponseEntity.noContent().build();
    }
}
