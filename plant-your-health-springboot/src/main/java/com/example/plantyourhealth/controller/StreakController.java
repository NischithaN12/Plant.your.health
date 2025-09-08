package com.example.plantyourhealth.controller;

import com.example.plantyourhealth.dto.LogActivityRequest;
import com.example.plantyourhealth.exception.NotFoundException;
import com.example.plantyourhealth.model.Activity;
import com.example.plantyourhealth.model.ActivityType;
import com.example.plantyourhealth.model.Streak;
import com.example.plantyourhealth.service.ActivityService;
import com.example.plantyourhealth.service.StreakService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class StreakController {
    private final ActivityService activityService;
    private final StreakService streakService;

    public StreakController(ActivityService activityService, StreakService streakService) {
        this.activityService = activityService;
        this.streakService = streakService;
    }

    @PostMapping("/logs")
    public ResponseEntity<Streak> log(@Valid @RequestBody LogActivityRequest req) {
        Activity a = activityService.getOrThrow(req.getActivityId());
        Streak updated = streakService.log(a);
        return ResponseEntity.ok(updated);
    }


    @GetMapping("/streaks")
    public Map<ActivityType, Streak> all() {
        return streakService.getAllStreaks();
    }

    @GetMapping("/streaks/{type}")
    public Streak one(@PathVariable String type) {
        try {
            ActivityType activityType = ActivityType.valueOf(type.toUpperCase());
            return streakService.getStreak(activityType);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Invalid activity type: " + type);
        }
    }
}
