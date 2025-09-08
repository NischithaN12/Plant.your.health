package com.example.plantyourhealth.controller;

import com.example.plantyourhealth.dto.CreateActivityRequest;
import com.example.plantyourhealth.dto.UpdateActivityRequest;
import com.example.plantyourhealth.exception.BadRequestException;
import com.example.plantyourhealth.model.Activity;
import com.example.plantyourhealth.model.ActivityType;
import com.example.plantyourhealth.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    // ✅ Get all activities or filter by type
    @GetMapping
    public List<Activity> list(@RequestParam(required = false) String type) {
        if (type != null) {
            try {
                ActivityType activityType = ActivityType.valueOf(type.toUpperCase());
                return activityService.all(activityType);
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid activity type: " + type);
            }
        }
        return activityService.all(null);
    }

    // ✅ Create new activity
    @PostMapping
    public ResponseEntity<Activity> create(@Valid @RequestBody CreateActivityRequest req) {
        if (req.getSuggested() == null) req.setSuggested(false);
        Activity created = activityService.create(req);
        return ResponseEntity.created(URI.create("/api/activities/" + created.getId()))
                .body(created);
    }

    // ✅ Update activity (by id)
    @PutMapping("/{id}")

    public Activity update(@PathVariable Long id,
                           @Valid @RequestBody UpdateActivityRequest req) {
        return activityService.update(id, req);
    }

    // ✅ Delete activity (by id)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        activityService.delete(id);
        return ResponseEntity.ok("Activity with id " + id + " deleted successfully.");
    }
}
