# Plant Your Health - Spring Boot

Run locally:
```bash
./mvnw spring-boot:run
```
(or `mvn spring-boot:run` if Maven is installed)

Endpoints:
- `GET /api/activities?type=EXERCISE|FOOD|YOGA|HYDRATION`
- `POST /api/activities` (name, type, suggested=false)  -> add custom or suggested
- `PUT /api/activities/{id}` -> update (blocked for suggested)
- `DELETE /api/activities/{id}` -> delete (allowed for both suggested & custom)
- `POST /api/logs` (activityId) -> log completion; updates streak for that activity's type
- `GET /api/streaks` -> map of type -> streak
- `GET /api/streaks/{type}` -> single streak

Notes:
- Suggested activities are preloaded for all four types.
- Updating suggested activities is blocked (you may delete and re-add as custom).
- Streak logic uses Asia/Kolkata timezone. Logging once per day per type increases the streak.
- A daily scheduler at 02:00 IST resets `currentStreak` to 0 for categories that missed yesterday (so we don't recalc repeatedly).
- H2 in-memory DB with console at `/h2`.
