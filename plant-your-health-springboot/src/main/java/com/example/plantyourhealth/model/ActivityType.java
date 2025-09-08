package com.example.plantyourhealth.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ActivityType {
    EXERCISE, FOOD, YOGA, HYDRATION;

    @JsonCreator
    public static ActivityType fromString(String input) {
        try {
            return ActivityType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid activity type: " + input);
        }
    }
}
