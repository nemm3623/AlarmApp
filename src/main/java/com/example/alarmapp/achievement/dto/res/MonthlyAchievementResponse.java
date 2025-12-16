package com.example.alarmapp.achievement.dto.res;

import java.util.List;

public record MonthlyAchievementResponse(
        long monthlyPercent,
        String month,
        int complete,
        int snooze,
        int cancel,
        List<DailyAchievementItem> daily
) {}
