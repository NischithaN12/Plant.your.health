package com.example.plantyourhealth.dto;

import com.example.plantyourhealth.model.ActivityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateActivityRequest {
    @NotBlank
    private String name;


    // Getters/Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


}
