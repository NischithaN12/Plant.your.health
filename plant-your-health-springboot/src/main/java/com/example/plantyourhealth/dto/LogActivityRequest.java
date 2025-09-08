package com.example.plantyourhealth.dto;

import jakarta.validation.constraints.NotNull;

public class LogActivityRequest {
    @NotNull
    private Long activityId;

    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }
}
