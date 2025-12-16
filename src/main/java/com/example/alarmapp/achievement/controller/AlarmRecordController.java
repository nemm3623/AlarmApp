package com.example.alarmapp.achievement.controller;

import com.example.alarmapp.achievement.enums.AlarmRecordStatus;
import com.example.alarmapp.achievement.service.AlarmRecordService;
import com.example.alarmapp.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/records")
public class AlarmRecordController {

    private final AlarmRecordService alarmRecordService;

    @PostMapping("/{alarmId}/complete")
    public void complete(
            @PathVariable Long alarmId,
            Authentication authentication
    ) {
        Member member = (Member) authentication.getPrincipal();
        alarmRecordService.record(member, alarmId, AlarmRecordStatus.COMPLETE);
    }

    @PostMapping("/{alarmId}/snooze")
    public void snooze(
            @PathVariable Long alarmId,
            Authentication authentication
    ) {
        Member member = (Member) authentication.getPrincipal();
        alarmRecordService.record(member, alarmId, AlarmRecordStatus.SNOOZE);
    }

    @PostMapping("/{alarmId}/cancel")
    public void cancel(
            @PathVariable Long alarmId,
            Authentication authentication
    ) {
        Member member = (Member) authentication.getPrincipal();
        alarmRecordService.record(member, alarmId, AlarmRecordStatus.CANCEL);
    }
}
