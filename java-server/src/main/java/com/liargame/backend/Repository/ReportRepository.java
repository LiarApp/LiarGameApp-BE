package com.liargame.backend.Repository;

import com.liargame.backend.Entity.Report;
import com.liargame.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByUser(User user);
    void deleteByUserAndReportedUser(User user, User reportedUser);
}
