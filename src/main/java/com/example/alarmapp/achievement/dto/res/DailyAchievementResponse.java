package com.example.alarmapp.achievement.dto.res;

public record DailyAchievementResponse(
        String date,
        int percent,
        int complete,
        int snooze,
        int cancel
) {}
