package com.liargame.backend.Service;

import com.liargame.backend.DTO.ReportDTO.ReportRequest;
import com.liargame.backend.DTO.ReportDTO.ReportResponse;
import com.liargame.backend.Entity.Report;
import com.liargame.backend.Entity.User;
import com.liargame.backend.Repository.ReportRepository;
import com.liargame.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    public void createReport(Long userId, Long reportedUserId, String reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));
        User reportedUser = userRepository.findById(reportedUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + reportedUserId));

        Report report = new Report();
        report.setUser(user);
        report.setReportedUser(reportedUser);
        report.setReason(reason);

        reportRepository.save(report);
    }

    public List<ReportResponse> getAll() {
        List<Report> reportList = reportRepository.findAll();
        List<ReportResponse> reportResponseList = new ArrayList<>();

        for (Report r : reportList) {
            User reportedUser = r.getReportedUser();
            ReportResponse reportResponse = new ReportResponse(
                    reportedUser.getId(),
                    reportedUser.getNickname(),
                    reportedUser.getProfileImg(),
                    r.getReason()
            );
            reportResponseList.add(reportResponse);
        }

        return reportResponseList;
    }

    public List<ReportResponse> getReportedUsers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));

        List<Report> reportList = reportRepository.findByUser(user);
        List<ReportResponse> reportResponseList = new ArrayList<>();

        for (Report r : reportList) {
            User reportedUser = r.getReportedUser();
            ReportResponse reportResponse = new ReportResponse(
                    reportedUser.getId(),
                    reportedUser.getNickname(),
                    reportedUser.getProfileImg(),
                    r.getReason()
            );
            reportResponseList.add(reportResponse);
        }

        return reportResponseList;
    }

    @Transactional
    public void cancelReport(Long userId, Long reportedUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + userId));
        User reportedUser = userRepository.findById(reportedUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다: " + reportedUserId));

        reportRepository.deleteByUserAndReportedUser(user, reportedUser);
    }
}
