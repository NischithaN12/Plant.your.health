package com.example.plantyourhealth.service;

import com.example.plantyourhealth.dto.CreateActivityRequest;
import com.example.plantyourhealth.dto.UpdateActivityRequest;
import com.example.plantyourhealth.exception.BadRequestException;
import com.example.plantyourhealth.exception.NotFoundException;
import com.example.plantyourhealth.model.Activity;
import com.example.plantyourhealth.model.ActivityType;
import com.example.plantyourhealth.repo.ActivityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> all(ActivityType type) {
        if (type == null) return activityRepository.findAll();
        return activityRepository.findByType(type);
    }

    @Transactional
    public Activity create(CreateActivityRequest req) {
        activityRepository.findByNameAndType(req.getName().trim(), req.getType())
                .ifPresent(a -> { throw new BadRequestException("Activity already exists in this type"); });
        Activity a = new Activity();
        a.setName(req.getName().trim());
        a.setType(req.getType());

        return activityRepository.save(a);
    }

    @Transactional
    public Activity update(Long id, UpdateActivityRequest req) {
        Activity a = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Activity not found"));

        // Update fields
        a.setName(req.getName().trim());
        return activityRepository.save(a);
    }

    @Transactional
    public void delete(Long id) {
        Activity a = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Activity not found"));
        activityRepository.delete(a);
    }

    public Activity getOrThrow(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Activity not found"));
    }
}
