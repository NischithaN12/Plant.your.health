package com.example.plantyourhealth.controller;

import com.example.plantyourhealth.dto.LogActivityRequest;
import com.example.plantyourhealth.exception.NotFoundException;
import com.example.plantyourhealth.model.Activity;
import com.example.plantyourhealth.model.ActivityLog;
import com.example.plantyourhealth.model.ActivityType;
import com.example.plantyourhealth.model.Streak;
import com.example.plantyourhealth.service.ActivityService;
import com.example.plantyourhealth.service.StreakService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
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

    @DeleteMapping("/logs/{activityId}")
    public ResponseEntity<Void> unlog(@PathVariable Long activityId) {
        Activity activity = activityService.getOrThrow(activityId);
        streakService.unlog(activity);
        return ResponseEntity.noContent().build();
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
    @GetMapping("/logs")
    public List<ActivityLog> getLogs(
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "today") String range) {

        List<ActivityLog> logs;

        // Determine which logs to fetch
        if ("all".equalsIgnoreCase(range)) {
            logs = streakService.getAllLogs();       // fetch all logs
        } else {
            LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
            logs = streakService.getTodayLogs(today); // fetch today logs
        }

        // Optional filter by type
        if (type != null) {
            logs = logs.stream()
                    .filter(log -> log.getType().toString().equalsIgnoreCase(type))
                    .toList();
        }

        return logs;
    }





}
