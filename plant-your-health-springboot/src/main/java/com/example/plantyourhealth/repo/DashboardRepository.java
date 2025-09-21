package com.example.plantyourhealth.repo;

import com.example.plantyourhealth.model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DashboardRepository extends JpaRepository<ActivityLog, Long> {
    // Derived method to get all logs for a given date
    List<ActivityLog> findAllByDate(LocalDate date);

    // Derived method to get all logs between two dates
    List<ActivityLog> findAllByDateBetween(LocalDate start, LocalDate end);
}
