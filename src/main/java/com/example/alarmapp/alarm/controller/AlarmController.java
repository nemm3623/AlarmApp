package com.example.alarmapp.alarm.controller;


import com.example.alarmapp.alarm.dto.req.CreateAlarmReqDTO;
import com.example.alarmapp.alarm.dto.req.UpdateAlarmReqDTO;
import com.example.alarmapp.alarm.dto.res.AlarmResDTO;
import com.example.alarmapp.alarm.service.AlarmCommandService;
import com.example.alarmapp.alarm.service.AlarmQueryService;
import com.example.alarmapp.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarms")
public class AlarmController {

    private final AlarmCommandService alarmCommandService;
    private final AlarmQueryService alarmQueryService;

    @GetMapping
    public ResponseEntity<List<AlarmResDTO>> getAllAlarms(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        return ResponseEntity.ok(alarmQueryService.allAlarms(member));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlarmResDTO> getAlarmById(@PathVariable Long id) {
        return ResponseEntity.ok(alarmQueryService.getAlarm(id));
    }

    @PostMapping("/create")
    public ResponseEntity<AlarmResDTO> createAlarm( Authentication authentication,
                                                    @RequestBody CreateAlarmReqDTO dto) {
        Member member = (Member) authentication.getPrincipal();
        return ResponseEntity.ok(alarmCommandService.createAlarm(dto, member));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AlarmResDTO> updateAlarm(@PathVariable Long id, @RequestBody UpdateAlarmReqDTO dto) {

        return ResponseEntity.ok(alarmCommandService.updateAlarm(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable Long id) {

        alarmCommandService.deleteAlarm(id);
        return ResponseEntity.noContent().build();
    }
}
