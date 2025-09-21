package com.example.plantyourhealth.service;

import com.example.plantyourhealth.model.Activity;
import com.example.plantyourhealth.model.ActivityLog;
import com.example.plantyourhealth.model.ActivityType;
import com.example.plantyourhealth.model.Streak;
import com.example.plantyourhealth.repo.ActivityLogRepository;
import com.example.plantyourhealth.repo.StreakRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class StreakService {

    private final StreakRepository streakRepository;
    private final ActivityLogRepository logRepository;

    private final ZoneId zoneId = ZoneId.of("Asia/Kolkata");

    public StreakService(StreakRepository streakRepository, ActivityLogRepository logRepository) {
        this.streakRepository = streakRepository;
        this.logRepository = logRepository;
    }

    private Streak getOrCreate(ActivityType type) {
        return streakRepository.findByType(type).orElseGet(() -> streakRepository.save(new Streak(type)));
    }

    @Transactional
    public Streak log(Activity activity) {
        LocalDate today = LocalDate.now(zoneId);
        Streak s = getOrCreate(activity.getType());

        if (!logRepository.existsByActivityIdAndDate(activity.getId(), today)) {
            int points = activity.isSuggested() ? 20 : 10; // ✅ assign points
            logRepository.save(new ActivityLog(activity.getId(), activity.getType(), today, points));


            if (s.getLastActiveDate() == null) {
                s.setCurrentStreak(1);
            } else if (s.getLastActiveDate().equals(today)) {
                return s; // already counted today
            } else if (s.getLastActiveDate().equals(today.minusDays(1))) {
                s.setCurrentStreak(s.getCurrentStreak() + 1);
            } else {
                s.setCurrentStreak(1);
            }

            s.setBestStreak(Math.max(s.getBestStreak(), s.getCurrentStreak()));
            s.setLastActiveDate(today);
            return streakRepository.save(s);
        }

        return s;
    }

    @Transactional
    public void unlog(Activity activity) {
        LocalDate today = LocalDate.now(zoneId);

        // Check if today's log exists
        if (logRepository.existsByActivityIdAndDate(activity.getId(), today)) {
            // Delete today's log
            logRepository.deleteByActivityIdAndDate(activity.getId(), today);

            Streak s = getOrCreate(activity.getType());

            // Rollback streak only if last active date was today
            if (s.getLastActiveDate() != null && s.getLastActiveDate().equals(today)) {
                // Find the most recent previous log date (if any)
                LocalDate previousDate = logRepository
                        .findTopByActivityIdOrderByDateDesc(activity.getId())
                        .map(ActivityLog::getDate)
                        .orElse(null);

                if (previousDate != null) {
                    // Restore streak up to previous date
                    s.setLastActiveDate(previousDate);

                    // Check if yesterday was last logged → maintain streak
                    if (previousDate.equals(today.minusDays(1))) {
                        s.setCurrentStreak(s.getCurrentStreak() - 1);
                    } else {
                        // Break streak if there’s a gap
                        s.setCurrentStreak(0);
                    }
                } else {
                    // No logs left → reset streak
                    s.setCurrentStreak(0);
                    s.setLastActiveDate(null);
                }

                streakRepository.save(s);
            }
        }
    }





    @Transactional
    public void dailyConsolidation() {
        // If a category missed yesterday and lastActiveDate < yesterday, mark currentStreak = 0
        LocalDate today = LocalDate.now(zoneId);
        LocalDate yesterday = today.minusDays(1);
        for (ActivityType t : ActivityType.values()) {
            Streak s = getOrCreate(t);
            if (s.getLastActiveDate() == null) continue;
            if (s.getLastActiveDate().isBefore(yesterday)) {
                s.setCurrentStreak(0);
                streakRepository.save(s);
            }
        }
    }
    public List<ActivityLog> getTodayLogs(LocalDate date) {
        return logRepository.findAllByDate(date);
    }


    public Map<ActivityType, Streak> getAllStreaks() {
        Map<ActivityType, Streak> map = new EnumMap<>(ActivityType.class);
        for (ActivityType t : ActivityType.values()) {
            map.put(t, getOrCreate(t));
        }
        return map;
    }
    public List<ActivityLog> getAllLogs() {
        return logRepository.findAll();
    }


    public Streak getStreak(ActivityType type) {
        return getOrCreate(type);
    }
}
