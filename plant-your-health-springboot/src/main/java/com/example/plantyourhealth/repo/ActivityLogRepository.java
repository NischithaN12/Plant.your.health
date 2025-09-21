package com.example.plantyourhealth.repo;

import com.example.plantyourhealth.model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    boolean existsByActivityIdAndDate(Long activityId, LocalDate date);

    void deleteByActivityIdAndDate(Long activityId, LocalDate date);

    Optional<ActivityLog> findByActivityIdAndDate(Long activityId, LocalDate date);

    Optional<ActivityLog> findTopByActivityIdOrderByDateDesc(Long activityId);
    List<ActivityLog> findAllByDate(LocalDate date);

}
