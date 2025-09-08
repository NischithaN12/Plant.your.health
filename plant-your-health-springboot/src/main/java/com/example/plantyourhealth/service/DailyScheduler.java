package com.example.plantyourhealth.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyScheduler {
    private final StreakService streakService;

    public DailyScheduler(StreakService streakService) {
        this.streakService = streakService;
    }

    // Run at 02:00 AM Asia/Kolkata every day
    @Scheduled(cron = "0 0 2 * * *", zone = "Asia/Kolkata")
    public void consolidate() {
        streakService.dailyConsolidation();
    }
}
