package com.example.plantyourhealth.controller;

import com.example.plantyourhealth.model.ActivityLog;
import com.example.plantyourhealth.repo.DashboardRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    private final DashboardRepository dashboardRepository;

    public DashboardController(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    // --- Today's points ---
    @GetMapping("/points/today")
    public int getTodayPoints() {
        List<ActivityLog> logs = dashboardRepository.findAllByDate(LocalDate.now(ZoneId.of("Asia/Kolkata")));
        return logs.stream().mapToInt(ActivityLog::getPoints).sum();
    }

    // --- Points trend between two dates ---
    @GetMapping("/points/trend")
    public Map<LocalDate, Integer> getPointsTrend(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        List<ActivityLog> logs = dashboardRepository.findAllByDateBetween(start, end);

        return logs.stream()
                .collect(Collectors.groupingBy(
                        ActivityLog::getDate,
                        LinkedHashMap::new,
                        Collectors.summingInt(ActivityLog::getPoints)
                ));
    }

    // --- Daily points for last month (for calendar) ---
    @GetMapping("/daily-points")
    public List<DailyPoints> getDailyPointsHistory() {
        LocalDate start = LocalDate.now().minusMonths(1);
        LocalDate end = LocalDate.now();

        List<ActivityLog> logs = dashboardRepository.findAllByDateBetween(start, end);

        Map<LocalDate, Integer> dailyPoints = logs.stream()
                .collect(Collectors.groupingBy(
                        ActivityLog::getDate,
                        LinkedHashMap::new,
                        Collectors.summingInt(ActivityLog::getPoints)
                ));

        return dailyPoints.entrySet().stream()
                .map(e -> new DailyPoints(e.getKey(), e.getValue()))
                .toList();
    }

    // DTO for frontend
    public record DailyPoints(LocalDate date, int points) {}
}
