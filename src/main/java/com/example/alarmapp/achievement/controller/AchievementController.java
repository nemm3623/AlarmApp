package com.example.alarmapp.achievement.controller;

import com.example.alarmapp.achievement.dto.res.*;
import com.example.alarmapp.achievement.service.AchievementService;
import com.example.alarmapp.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/achievement")
public class AchievementController {

    private final AchievementService service;

    @GetMapping("/today")
    public DailyAchievementResponse today(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        return service.getToday(member);
    }

    @GetMapping("/weekly")
    public WeeklyAchievementResponse weekly(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        return service.getWeekly(member);
    }

    @GetMapping("/monthly")
    public MonthlyAchievementResponse monthly(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        return service.getMonthly(member);
    }
}
