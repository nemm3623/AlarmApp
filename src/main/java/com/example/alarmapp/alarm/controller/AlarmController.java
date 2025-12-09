package com.example.alarmapp.alarm.controller;

import com.example.alarmapp.alarm.dto.res.DeleteAlarmResDTO;
import com.example.alarmapp.alarm.dto.req.CreateAlarmReqDTO;
import com.example.alarmapp.alarm.dto.req.UpdateAlarmReqDTO;
import com.example.alarmapp.alarm.dto.res.AlarmResDTO;
import com.example.alarmapp.alarm.dto.res.CreateAlarmResDTO;
import com.example.alarmapp.alarm.dto.res.UpdateAlarmResDTO;
import com.example.alarmapp.alarm.service.AlarmCommandService;
import com.example.alarmapp.alarm.service.AlarmQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarms")
public class AlarmController {

    private final AlarmCommandService alarmCommandService;
    private final AlarmQueryService alarmQueryService;

    @GetMapping
    public ResponseEntity<List<AlarmResDTO>> getAllAlarms() {
        return ResponseEntity.ok(alarmQueryService.allAlarms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlarmResDTO> getAlarmById(@PathVariable Long id) {
        return ResponseEntity.ok(alarmQueryService.getAlarm(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CreateAlarmResDTO> createAlarm( @RequestBody CreateAlarmReqDTO dto) {

        return ResponseEntity.ok(alarmCommandService.createAlarm(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateAlarmResDTO> updateAlarm(@PathVariable Long id, @RequestBody UpdateAlarmReqDTO dto) {

        return ResponseEntity.ok(alarmCommandService.updateAlarm(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAlarm(@PathVariable Long id) {

        alarmCommandService.deleteAlarm(id);
        return ResponseEntity.noContent().build();
    }
}
