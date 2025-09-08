package com.example.plantyourhealth.service;

import com.example.plantyourhealth.dto.CreateActivityRequest;
import com.example.plantyourhealth.model.Activity;
import com.example.plantyourhealth.model.ActivityType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final ActivityService activityService;

    public DataSeeder(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedSuggestions(ActivityType.EXERCISE, List.of("Run","Sprint","Situps","Plank"));
        seedSuggestions(ActivityType.FOOD, List.of("Dryfruits","Vegetables","Salad","Fruits"));
        seedSuggestions(ActivityType.YOGA, List.of("Asanas","Meditation","Pranayama"));
        seedSuggestions(ActivityType.HYDRATION, List.of("Water 2L","Herbal Tea","Coconut Water"));
    }

    private void seedSuggestions(ActivityType type, List<String> names) {
        for (String n : names) {
            try {
                CreateActivityRequest r = new CreateActivityRequest();
                r.setName(n);
                r.setType(type);
                r.setSuggested(true);
                activityService.create(r);
            } catch (Exception ignored) {}
        }
    }
}
