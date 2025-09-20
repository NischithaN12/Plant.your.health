package com.example.plantyourhealth.model;

import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "activity_logs")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long activityId;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    private LocalDate date;

    @Column(name = "points") // <--- explicitly map to column
    private int points;

    public ActivityLog() {
        // Required by JPA
    }

    public ActivityLog(Long activityId, ActivityType type, LocalDate date, int points) {
        this.activityId = activityId;
        this.type = type;
        this.date = date;
        this.points = points;
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }

    public ActivityType getType() { return type; }
    public void setType(ActivityType type) { this.type = type; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
