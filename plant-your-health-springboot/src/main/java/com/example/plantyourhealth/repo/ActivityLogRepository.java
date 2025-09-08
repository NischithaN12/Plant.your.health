package com.example.plantyourhealth.repo;

import com.example.plantyourhealth.model.ActivityLog;
import com.example.plantyourhealth.model.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    boolean existsByTypeAndDate(ActivityType type, LocalDate date);
    Optional<ActivityLog> findFirstByTypeOrderByDateDesc(ActivityType type);
}
