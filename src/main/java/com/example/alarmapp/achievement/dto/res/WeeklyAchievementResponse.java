package com.example.alarmapp.achievement.dto.res;

import java.util.List;

public record WeeklyAchievementResponse(
        long weeklyPercent,
        String weekStart,
        String weekEnd,
        int complete,
        int snooze,
        int cancel,
        List<DailyAchievementItem> daily
) {}
