package com.example.plantyourhealth.dto;

import com.example.plantyourhealth.model.ActivityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateActivityRequest {
    @NotBlank
    private String name;
    @NotNull
    private ActivityType type;
    private Boolean suggested = false;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ActivityType getType() { return type; }
    public void setType(ActivityType type) { this.type = type; }
    public Boolean getSuggested() { return suggested; }
    public void setSuggested(Boolean suggested) { this.suggested = suggested; }
}
