package com.example.plantyourhealth.repo;

import com.example.plantyourhealth.model.Streak;
import com.example.plantyourhealth.model.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreakRepository extends JpaRepository<Streak, Long> {
    Optional<Streak> findByType(ActivityType type);
}
