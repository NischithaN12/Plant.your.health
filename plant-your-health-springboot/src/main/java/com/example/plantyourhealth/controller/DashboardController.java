package com.example.plantyourhealth.controller;

import com.example.plantyourhealth.repo.ActivityLogRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")

public class DashboardController {

    private final ActivityLogRepository logRepository;

    public DashboardController(ActivityLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping("/points/today")
    public int getTodayPoints() {
        return logRepository.getTotalPointsByDate(LocalDate.now(ZoneId.of("Asia/Kolkata")));
    }

    @GetMapping("/points/trend")
    public Map<LocalDate, Integer> getPointsTrend(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        List<Object[]> results = logRepository.getPointsTrend(start, end);
        Map<LocalDate, Integer> trend = new LinkedHashMap<>();
        for (Object[] row : results) {
            trend.put((LocalDate) row[0], ((Number) row[1]).intValue());
        }
        return trend;
    }
}
