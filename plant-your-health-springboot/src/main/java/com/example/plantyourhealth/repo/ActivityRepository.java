package com.example.plantyourhealth.repo;

import com.example.plantyourhealth.model.Activity;
import com.example.plantyourhealth.model.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByType(ActivityType type);
    Optional<Activity> findByNameAndType(String name, ActivityType type);

}
