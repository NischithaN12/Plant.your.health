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

    // ✅ Sum points for a given date
    @Query("SELECT COALESCE(SUM(a.points), 0) FROM ActivityLog a WHERE a.date = :date")
    int getTotalPointsByDate(@Param("date") LocalDate date);

    // ✅ Sum points between two dates (trend)
    @Query("SELECT a.date, SUM(a.points) FROM ActivityLog a WHERE a.date BETWEEN :start AND :end GROUP BY a.date ORDER BY a.date")
    List<Object[]> getPointsTrend(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
